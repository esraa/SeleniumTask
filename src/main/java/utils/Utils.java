package utils;

public class Utils {

	private static int getRandomNumber(int min, int max) {
		int x = (int) (Math.random() * ((max - min) + 1)) + min;
		return x;
	}

	public static String getNewEmail(String email)
	{
		String[] emailStr = email.split("\\@");
		int randomNo = Utils.getRandomNumber(1, 50);
		email = emailStr[0].concat("+").concat(Integer.toString(randomNo)).concat("@").concat(emailStr[1]);
		return email;
	}

}
