public class BuilderPattern {
    public static void main(String[] args) {
        Computer basic = new Computer.Builder("i5", "32gb").setStorage("512gb").build();
        System.out.println(basic);
    }

    // Nested Computer class
    public static class Computer {
        private String cpu;
        private String ram;
        private String storage;

        private Computer(Builder builder) {
            this.cpu = builder.cpu;
            this.ram = builder.ram;
            this.storage = builder.storage;
        }

        public static class Builder {
            private String cpu;
            private String ram;
            private String storage;

            public Builder(String cpu, String ram) {
                this.cpu = cpu;
                this.ram = ram;
            }

            public Builder setStorage(String storage) {
                this.storage = storage;
                return this;
            }

            public Computer build() {
                return new Computer(this);
            }
        }

        @Override
        public String toString() {
            return "Computer [CPU=" + cpu + ", RAM=" + ram + ", Storage=" + storage + "]";
        }
    }
}
