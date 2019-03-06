package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static int[] computePrefixFunction(String pattern) {
		int m = pattern.length();
		int [] pi = new int [m]; //pi is the failure function
		pi [0] = 0;
		int k = 0;

		for (int q=1; q<m; q++) {
			while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
				k = pi[k-1];
			}
			if (pattern.charAt(k) == pattern.charAt(q)){
				k = k+1;
			}
			pi[q] = k;
		}

		return pi;
	}


	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}

		public void search() {
			//int [] pi = computePrefixFunction(pattern);
			int [] pi = prefixFunction;
			int q = 0;
			
			if (patternLen <= textLen) {
				for (int i=0; i<textLen; i++){
					while (q>0 && (pattern.charAt(q) != text.charAt(i))){
						q=pi[q-1];
					}
					if (pattern.charAt(q) == text.charAt(i)){
						q=q+1;
					}
					if (q==patternLen) {
						matchIndices.enqueue(i- patternLen +1);
						q=pi[q-1];
					}
				}

			}
		}
	}	
	public static void main(String args[]){
		//String txt = "ABABDABACDABABCABAB";
		//String pat = "ABABCABAB";

		//computePrefixFunction(pat);
		//Matcher.testPrefixFunction(pat);
		//Matcher.testKMPMatcher(146, 62);
		//Generates t random patterns of length l and finds their occurrences in a fixed text

		int  p = 10;
		int t = 100;
		String f = "matcherTimes.txt";

		//Matcher.getRuntimes(p, t, f);

		String fRatios = "matcherRatios.txt";
		int xOver = 50000;

		//Matcher.getRatios(p, t, xOver, fRatios);

		//to plot
		double c = 0.011660;         //maximum constant
		double a = 0.008488;        //average
		Matcher.plotRuntimes(c, a, f);

	}
}