package testutil;

import java.util.Random;

public class Processor<T> {

	interface Instruction {
		boolean make();
	}

	interface Success {
		void success();
	}

	interface Failure {
		void failure();
	}

	public static void main(String[] args) {
		Processor<String> processor = new Processor<>();
		for (int i = 0; i < 10; i++) {
			processor.process(new Instruction() {
				@Override
				public boolean make() {
					Random r = new Random();
					return r.nextInt(2)	> 0;				
				}
			}).success(new Success() {
				@Override
				public void success() {
					System.out.println("You made it!");
				}
			}).failure(new Failure() {
				@Override
				public void failure() {
					System.out.println("You didn't make it!");
				}
			});
		}
	}

	private boolean success_ = false;

	public Processor<T> process(Instruction instruction) {
		this.success_ = instruction.make();
		return this;
	}

	public Processor<T> success(Success success) {
		if (success_)
			success.success();
		return this;
	}

	public Processor<T> failure(Failure failure) {
		if (!success_) {
			failure.failure();
		}
		return this;
	}

}
