package solver.app;

import javax.swing.ImageIcon;

public class FlagPair {
	private String name;
	private int pairA, pairB;
	private boolean solved;
	private ImageIcon image = null;

	public FlagPair(String name, int pairA, int pairB, ImageIcon image) {
		super();
		this.name = name;
		this.pairA = pairA;
		this.pairB = pairB;
		this.image = image;

		this.solved = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPairA() {
		return pairA;
	}

	public void setPairA(int pairA) {
		this.pairA = pairA;
	}

	public int getPairB() {
		return pairB;
	}

	public void setPairB(int pairB) {
		this.pairB = pairB;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
}
