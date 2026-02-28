package org.mvel3.benchmark.domain;

public class FactionState {

    private String factionName;
    private int influence;
    private int stability;
    private double treasury;
    private boolean atWar;

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public int getInfluence() {
        return influence;
    }

    public void setInfluence(int influence) {
        this.influence = influence;
    }

    public int getStability() {
        return stability;
    }

    public void setStability(int stability) {
        this.stability = stability;
    }

    public double getTreasury() {
        return treasury;
    }

    public void setTreasury(double treasury) {
        this.treasury = treasury;
    }

    public boolean isAtWar() {
        return atWar;
    }

    public void setAtWar(boolean atWar) {
        this.atWar = atWar;
    }
}
