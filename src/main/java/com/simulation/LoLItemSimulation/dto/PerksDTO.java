package com.simulation.LoLItemSimulation.dto;

import java.util.List;

public class PerksDTO {
        private List<PerkStatsDTO> statPerks;
        private List<PerkStyleDTO> styles;

        public List<PerkStatsDTO> getStatPerks() {
            return statPerks;
        }

        public void setStatPerks(List<PerkStatsDTO> statPerks) {
            this.statPerks = statPerks;
        }

        public List<PerkStyleDTO> getStyles() {
            return styles;
        }

        public void setStyles(List<PerkStyleDTO> styles) {
            this.styles = styles;
        }

}
