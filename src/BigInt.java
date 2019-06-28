import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class BigInt {

	private HashMap<Integer, Integer> digits = new HashMap<Integer, Integer>();

	private int carry = 0;

	public BigInt(String number) {
		for (int i = 0; i < number.length(); ++i) {
			int digit = Character.digit(number.charAt(i), 10);
			if (digit != 0) {
				digits.put(number.length() - i, digit);
			}
		}
	}

	public BigInt() {

	}

	public BigInt add(BigInt a) {
		BigInt res = new BigInt();

		TreeSet<Integer> aSet = new TreeSet<Integer>(a.digits.keySet());
		TreeSet<Integer> tSet = new TreeSet<Integer>(digits.keySet());

		Iterator<Integer> ait = aSet.iterator();
		Iterator<Integer> tit = tSet.iterator();

		int aDig = ait.next();
		int aVal = a.digits.get(aDig);

		int tDig = tit.next();
		int tVal = digits.get(tDig);

		boolean aHas = true;
		boolean tHas = true;

		while (aHas || tHas) {

			if (aDig == tDig) {
				int sum;
				Integer b = res.digits.get(aDig);
				if (b != null) {
					sum = aVal + tVal + b;
				} else {
					sum = aVal + tVal;
				}
				int val = sum % 10;
				if (val != 0) {
					res.digits.put(aDig, val);
				} else {
					if (res.digits.get(tDig) != null) {
						res.digits.remove(tDig);
					}
				}
				if (sum > 9) {
					res.digits.put(aDig + 1, 1);
				}
				if (ait.hasNext()) {
					aDig = ait.next();
					aVal = a.digits.get(aDig);
				} else {
					aHas = false;
				}
				if (tit.hasNext()) {
					tDig = tit.next();
					tVal = digits.get(tDig);
				} else {
					tHas = false;
				}
			} else if ((aDig < tDig && aHas) || (aHas && !tHas)) {
				int sum;
				Integer b = res.digits.get(aDig);
				if (b != null) {
					sum = aVal + carry + b;
				} else {
					sum = aVal + carry;
				}
				int val = sum % 10;
				if (val != 0) {
					res.digits.put(aDig, val);
				} else {
					if (res.digits.get(aDig) != null) {
						res.digits.remove(aDig);
					}
				}
				if (sum > 9) {
					res.digits.put(aDig + 1, 1);
				}
				if (ait.hasNext()) {
					aDig = ait.next();
					aVal = a.digits.get(aDig);
				} else {
					aHas = false;
				}
			} else if ((tDig < aDig && tHas) || (tHas && !aHas)) {
				int sum;
				Integer b = res.digits.get(tDig);
				if (b != null) {
					sum = tVal + carry + b;
				} else {
					sum = tVal + carry;
				}
				int val = sum % 10;
				if (val != 0) {
					res.digits.put(tDig, val);
				} else {
					if (res.digits.get(tDig) != null) {
						res.digits.remove(tDig);
					}
				}
				if (sum > 9) {
					res.digits.put(tDig + 1, 1);
				}
				if (tit.hasNext()) {
					tDig = tit.next();
					tVal = digits.get(tDig);
				} else {
					tHas = false;
				}
			}
		}

		return res;
	}

	@Override
	public String toString() {
		String number = "";

		TreeSet<Integer> keys = new TreeSet<Integer>(digits.keySet());
		Iterator<Integer> it = keys.iterator();

		int last = 1;

		while (it.hasNext()) {
			int digit = it.next();
			if (digit == last) {
				number = digits.get(digit) + number;
				++last;
			} else {
				while (last < digit) {
					number = "0" + number;
					++last;
				}
				number = digits.get(digit) + number;
				++last;
			}
		}

		return number;
	}

}
