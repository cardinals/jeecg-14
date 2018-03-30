package test;

public class Test {
        public static void main(String[] args) {
                Test closure = new Test();
                for (int i = 1; i <= 10; i++) {
                        System.out.println(closure.create(i).increment(i));
                }
        }
 
        class InnerClosure {
                InnerClosure(int initial) {
                        this.initial = initial;
                }
                private final int initial;
 
                public int increment(int i) {
                        return this.initial + i;
                }
                @Override
                public InnerClosure clone() {
                        return this;
                }
        }
        public InnerClosure create(int initial) {
                return new InnerClosure(initial);
        }
}