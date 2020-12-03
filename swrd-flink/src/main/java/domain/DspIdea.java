package domain;

import java.io.Serializable;

/**
 * @author xinghonglin
 * @date 2020/12/03
 */
public class DspIdea implements Serializable {

    public Long dspId;
    public Long entityId;

    public DspIdea() {
    }

    public DspIdea(Long dspId, Long entityId) {
        this.dspId = dspId;
        this.entityId = entityId;
    }

    public Long getDspId() {
        return dspId;
    }

    public void setDspId(Long dspId) {
        this.dspId = dspId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "DspIdea{" +
                "dspId='" + dspId + '\'' +
                ", entityId=" + entityId +
                '}';
    }
}
