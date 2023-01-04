package cn.poryoung.htcpmd.center.constant;

import lombok.Getter;

public class HtcpmdStructureConstant {
    public static final String elementsSeparator = "-";
    public static final String getAllDebugCode = "ias@szu";

    @Getter
    public enum SearchModeEnum {
        Any(0, "Any"),
        All(1, "All");


        private final Integer code;

        private final String name;

        SearchModeEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    @Getter
    public enum CarrierType {
        P, N, M
    }

    @Getter
    public enum DiagramFiled {
        band("band", "Electrical Band"),
        phonon("phonon", "Phonon Dispersion"),
        eDos("eDos", "Electron Dos"),
        eKappa("eKappa", "Electrical Thermal Conductivity"),
        lKappa("lKappa", "Lattice Thermal Conductivity"),
        phononDos("phononDos", "Phonon Dos"),
        mobility("mobility", "mobility"),
        sigma("sigma", "Electrical Conductivity");
        private final String name;

        private final String desc;

        DiagramFiled(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }
}
