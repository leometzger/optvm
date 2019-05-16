package optvm.utils;

import optvm.constraints.ConstraintContext;
import optvm.entities.Cloud;
import optvm.entities.Datacenter;
import optvm.entities.Host;
import optvm.entities.VM;
import optvm.entities.constants.OS;
import optvm.entities.vos.CostInfo;
import optvm.entities.vos.EnergyInfo;
import optvm.entities.vos.Hardware;
import optvm.entities.vos.Localization;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OptVMFaker {
    private Random randomizer;

    // Number of entities
    private final int dcsPerCloud;
    private final int hostsPerDC;
    private final int vmPerHost;

    // BOUNDS
    private final int BW_SIZE_BOUND = 1000;
    private final int PROCESSING_UNITS_BOUND = 16;
    private final int MIPS_BOUND = 10000;
    private final int HOPS_BOUND = 100;

    // CONSTS
    private final List<OS> oss = Arrays.asList(OS.NIX, OS.WINDOWS);
    private final List<String> ARCHITECTURES = Arrays.asList("x86", "x64");
    private final List<String> COUNTRIES = Arrays.asList("USA", "ISRAEL", "BRAZIL", "URUGUAI", "ARGENTINA");
    private final List<String> HYPERVISORS = Arrays.asList("Xen", "KVM", "QEMU", "OpenVZ");

    private final List<Integer> VM_MEMORY = Arrays.asList(250, 500, 1000, 2000, 4000, 8000);
    private final List<Integer> VM_STORAGE = Arrays.asList(2000, 5000, 100000, 100000);
    private final List<Integer> VM_SIZES = Arrays.asList(250, 500, 1000);

    private final List<Integer> HOST_MEMORY = Arrays.asList(2000, 4000, 8000, 16000, 32000, 64000, 128000); // 2GB, 4GB...
    private final List<Integer> HOST_STORAGE = Arrays.asList(10000, 100000, 500000, 1000000, 2000000); // 10GB, 100GB ...

    public OptVMFaker() {
        this.randomizer = new Random();
        this.dcsPerCloud = 5;
        this.hostsPerDC = 20;
        this.vmPerHost = 5;
    }

    public OptVMFaker(int dcsPerCloud, int hostsPerDC, int vmPerHost) {
        this.randomizer = new Random();
        this.dcsPerCloud = dcsPerCloud;
        this.hostsPerDC = hostsPerDC;
        this.vmPerHost = vmPerHost;
    }

    public VM makeVM() {
        VM vm = new VM();

        Integer memory = getRandomIntFrom(VM_MEMORY);

        vm.setBw(Math.abs(this.randomizer.nextLong() % BW_SIZE_BOUND));
        vm.setRam(memory);
        vm.setStorage(getRandomIntFrom(VM_STORAGE));
        vm.setSize(getRandomIntFrom(VM_SIZES));
        vm.setIter(this.randomizer.nextInt(4) + 2);
        vm.setCurrentAllocatedMemory(Math.round(memory * (this.randomizer.nextFloat()))); // Percentagem da memoria
        vm.setCsId(this.randomizer.nextInt(100));
        vm.setDirtyPages(this.randomizer.nextInt(200));

        return vm;
    }

    public Host makeHost() {
        int memory = getRandomIntFrom(HOST_MEMORY);

        Hardware hardware = new Hardware(
                memory,
                this.randomizer.nextInt(PROCESSING_UNITS_BOUND),
                this.randomizer.nextInt(MIPS_BOUND),
                getRandomIntFrom(HOST_STORAGE)
        );

        EnergyInfo energyInfo = new EnergyInfo(
                this.randomizer.nextInt(3), // pmin
                this.randomizer.nextInt(3) + 3, // pmax
                this.randomizer.nextInt(100) // uso de cpu em %
        );

        return new Host(
                true,
                this.randomizer.nextInt(BW_SIZE_BOUND),
                this.randomizer.nextInt(HOPS_BOUND),
                this.randomizer.nextInt(memory),
                energyInfo,
                getRandomStringFrom(HYPERVISORS),
                hardware
        );
    }

    public Datacenter makeDatacenter() {
        Datacenter datacenter = new Datacenter();

        CostInfo costInfo = new CostInfo();
        costInfo.setCostPerBw(round(Math.abs(this.randomizer.nextDouble()), 2));
        costInfo.setCostPerMem(round(Math.abs(this.randomizer.nextDouble()), 2));
        costInfo.setCostPerSec(round(Math.abs(this.randomizer.nextDouble()), 2));
        costInfo.setCostPerStorage(round(Math.abs(this.randomizer.nextDouble()), 2));

        Localization localization = new Localization();
        localization.setCountry(this.getRandomStringFrom(COUNTRIES));

        datacenter.setArchitecture(this.getRandomStringFrom(ARCHITECTURES));
        datacenter.setCostInfo(costInfo);
        datacenter.setLocalization(localization);
        datacenter.setOs(getRandomOS(oss));

        return datacenter;
    }

    public Cloud makeCloud(String name) {
        return new Cloud(name);
    }

    public Localization makeLocalization() {
        Localization localization = new Localization();
        localization.setCountry(this.getRandomStringFrom(COUNTRIES));
        return localization;
    }

    private OS getRandomOS(List<OS> oss) {
        int index = this.randomizer.nextInt(oss.size());
        return oss.get(index);
    }

    private Integer getRandomIntFrom(List<Integer> numbers) {
        int index = this.randomizer.nextInt(numbers.size());
        return numbers.get(index);
    }

    private String getRandomStringFrom(List<String> strings) {
        int index = this.randomizer.nextInt(strings.size());
        return strings.get(index);
    }

    public ConstraintContext makeFakeConstraintContext(String cloudName, List<Cloud> clouds) {
        VM vm = this.makeVM();
        Host fromHost = this.makeHost();
        fromHost.setHops(30);

        Datacenter fromDC = this.makeDatacenter();
        fromDC.setLocalization(this.makeLocalization());
        Cloud fromCloud = this.makeCloud(cloudName);

        ConstraintContext context = new ConstraintContext();
        context.setPossibleTargets(clouds);
        context.setVm(vm);
        context.setFromCloud(fromCloud);
        context.setFromDC(fromDC);
        context.setFromHost(fromHost);
        context.setPossibleTargets(clouds);

        return context;
    }

    public List<Cloud> makeFakeClouds(List<String> cloudNames) {
        List<Cloud> clouds = new ArrayList();

        for (String cloudName : cloudNames) {
            Cloud cloud = this.makeCloud(cloudName);
            cloud.setDatacenters(makeFakeDatacenters(cloud));

            clouds.add(cloud);
        }
        return clouds;
    }

    private List<Datacenter> makeFakeDatacenters(Cloud cloud) {
        List<Datacenter> datacenters = new ArrayList();

        for (int i = 0; i < dcsPerCloud; ++i) {
            String id = cloud.getId() + " DC(" + i + ")";

            Datacenter datacenter = this.makeDatacenter();
            datacenter.setId(id);
            datacenter.setHosts(makeFakeHosts(datacenter));

            datacenters.add(datacenter);
        }
        return datacenters;
    }

    private List<Host> makeFakeHosts(Datacenter datacenter) {
        List<Host> hosts = new ArrayList();

        for (int i = 0; i < hostsPerDC; ++i) {
            String id = datacenter.getId() + " HOST(" + i + ")";

            Host host = this.makeHost();
            host.setId(id);
            host.setVms(makeFakeVMs(host));

            hosts.add(host);
        }

        return hosts;
    }

    private List<VM> makeFakeVMs(Host host) {
        List<VM> vms = new ArrayList();

        for (int i = 0; i < vmPerHost; ++i) {
            VM vm = this.makeVM();
            vms.add(vm);
        }
        return vms;
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
