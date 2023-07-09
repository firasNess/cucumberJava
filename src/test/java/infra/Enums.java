package infra;

public class Enums {
    public enum UIStyle {
        OLD(1),
        NEW(2);

        private int value;

        private UIStyle(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum SystemUnderTest {
        PROD(1),
        PREPROD(2);

        private int value;

        private SystemUnderTest(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum WaitInterval {
        FAST(1),
        SHORT(5),
        MEDIUM(10),
        LONG(30),
        TOO_LONG(180);

        private int value;

        private WaitInterval(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum StepNumber {
        FIRST("app-first-step"),
        SECOND("app-second-step"),
        THIRD("app-third-step"),
        FOURTH("app-fourth-step"),
        FIFTH("app-fifth-step"),
        SIXTH("app-sixth-step");

        private String value;

        private StepNumber(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
