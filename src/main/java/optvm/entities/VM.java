package optvm.entities;

public class VM {

    private long ram;
    private int storage;
    private long size;
    private long bw;
    private long currentAllocatedMemory;
    private long dirtyPages;
    private long userId;
    private long csId;

    public VM() {
    }

    public long getCurrentAllocatedMemory() {
        return currentAllocatedMemory;
    }

    public void setCurrentAllocatedMemory(long currentAllocatedMemory) {
        this.currentAllocatedMemory = currentAllocatedMemory;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getBw() {
        return bw;
    }

    public void setBw(long bw) {
        this.bw = bw;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCsId() {
        return csId;
    }

    public void setCsId(long csId) {
        this.csId = csId;
    }

    public long getDirtyPages() {
        return dirtyPages;
    }

    public void setDirtyPages(long dirtyPages) {
        this.dirtyPages = dirtyPages;
    }

}
