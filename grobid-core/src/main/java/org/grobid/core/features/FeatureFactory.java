package org.grobid.core.features;

import org.grobid.core.lexicon.Lexicon;
import org.grobid.core.utilities.OffsetPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class providing a toolkit for managing and creating features or string sequence tagging problems.
 *
 * @author Patrice Lopez
 */
public class FeatureFactory {

    private static FeatureFactory instance;

    static {
        instance = new FeatureFactory();
    }

    public boolean newline = true;
    public Lexicon lexicon = Lexicon.getInstance();

    public Pattern YEAR = Pattern.compile("[1,2][0-9][0-9][0-9]");
    public Pattern HTTP = Pattern.compile("http");
    public Pattern isDigit = Pattern.compile("^\\d+$");
    public Pattern EMAIL2 = Pattern.compile("\\w+((\\.|\\-|_)\\w+)*@\\w+((\\.|\\-)\\w+)+");
    public Pattern EMAIL = Pattern.compile("^(?:[a-zA-Z0-9_'^&amp;/+-])+(?:\\.(?:[a-zA-Z0-9_'^&amp;/+-])+)*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.){3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)+(?:[a-zA-Z]){2,}\\.?)$");
    public Pattern ACRONYM = Pattern.compile("[A-Z]\\.([A-Z]\\.)*");
    public Pattern isPunct = Pattern.compile("^[\\,\\:;\\?\\.]+$");

    static public List<String> KEYWORDSPUB = new ArrayList<String>() {{
        add("Journal");
        add("journal");
        add("Proceedings");
        add("proceedings");
        add("Conference");
        add("conference");
        add("Workshop");
        add("workshop");
        add("Symposium");
        add("symposium");
    }};

    static public List<String> MONTHS = new ArrayList<String>() {{
        add("January");
        add("February");
        add("March");
        add("April");
        add("May");
        add("June");
        add("July");
        add("August");
        add("September");
        add("October");
        add("November");
        add("December");
        add("Jan");
        add("feb");
        add("Mar");
        add("Apr");
        add("May");
        add("Jun");
        add("Jul");
        add("Aug");
        add("Sep");
        add("Oct");
        add("Nov");
        add("Dec");
    }};

    static public List<String> COUNTRY_CODES = new ArrayList<String>() {{
        add("US");
        add("EP");
        add("WO");
        add("DE");
        add("AU");
        add("GB");
        add("DK");
        add("BE");
        add("AT");
        add("CN");
        add("KR");
        add("EA");
        add("CH");
        add("JP");
        add("FR");
        add("UK");
        add("RU");
        add("CA");
        add("NL");
        add("DD");
        add("SE");
        add("FI");
        add("MX");
        add("OA");
        add("AP");
        add("AR");
        add("BR");
        add("BG");
        add("CL");
        add("GR");
        add("HU");
        add("IS");
        add("IN");
        add("IE");
        add("IL");
        add("IT");
        add("LU");
        add("NO");
        add("NZ");
        add("PL");
        add("RU");
        add("ES");
        add("TW");
        add("TR");
    }};

    static public List<String> KIND_CODES = new ArrayList<String>() {{
        add("A");
        add("B");
        add("C");
        add("U");
        add("P");
    }};
    ;

    // hidden constructor
    private FeatureFactory() {
    }

    public static FeatureFactory getInstance() {
        return instance;
    }

    /**
     * Test if the first letter of the string is a capital letter
     */
    public boolean test_first_capital(String tok) {
        if (tok == null)
            return false;
        if (tok.length() == 0)
            return false;
        char a = tok.charAt(0);
        if (Character.isUpperCase(a))
            return true;
        else
            return false;
    }

    /**
     * Test if all the letters of the string are capital letters
     * (characters can be also digits which are then ignored)
     */
    public boolean test_all_capital(String tok) {
        if (tok == null)
            return false;
        if (tok.length() == 0)
            return false;
        char a;
        for (int i = 0; i < tok.length(); i++) {
            a = tok.charAt(i);
            if (Character.isLowerCase(a))
                return false;
        }
        return true;
    }

    /**
     * Test for a given character occurence in the string
     */
    public boolean test_char(String tok, char c) {
        if (tok == null)
            return false;
        if (tok.length() == 0)
            return false;
        int i = tok.indexOf(c);
        if (i == -1)
            return false;
        else
            return true;
    }

    /**
     * Test for the current string contains at least one digit
     */
    public boolean test_digit(String tok) {
        if (tok == null)
            return false;
        if (tok.length() == 0)
            return false;
        char a;
        for (int i = 0; i < tok.length(); i++) {
            a = tok.charAt(i);
            if (Character.isDigit(a))
                return true;
        }
        return false;
    }


    /**
     * Test for the current string contains only digit
     */
    public boolean test_number(String tok) {
        if (tok == null)
            return false;
        if (tok.length() == 0)
            return false;
        char a;
        for (int i = 0; i < tok.length(); i++) {
            a = tok.charAt(i);
            if (!Character.isDigit(a))
                return false;
        }
        return true;
    }


    /**
     * Test for the current string is a number or a decimal number, i.e. containing only digits or ",", "."
     */
    public boolean test_complex_number(String tok) {
        if (tok == null)
            return false;
        if (tok.length() == 0)
            return false;
        char a;
        for (int i = 0; i < tok.length(); i++) {
            a = tok.charAt(i);
            if ((!Character.isDigit(a)) & (a != ',') & (a != '.'))
                return false;
        }
        return true;
    }

    /**
     * Test if the current string is a common name
     */
    public boolean test_common(String tok) {
        if (tok == null)
            return false;
        else if (tok.length() == 0)
            return false;
        else
            return lexicon.inDictionary(tok.trim().toLowerCase());
    }

    /**
     * Test if the current string is a first name or family name
     */
    public boolean test_names(String tok) {
        return (lexicon.inFirstNames(tok.toLowerCase()) || lexicon.inLastNames(tok.toLowerCase()));
    }

    /**
     * Test if the current string is a family name
     */
    public boolean test_first_names(String tok) {
        return lexicon.inFirstNames(tok.toLowerCase());
    }

    /**
     * Test if the current string is a family name
     */
    public boolean test_last_names(String tok) {
        return lexicon.inLastNames(tok.toLowerCase());
    }

    /**
     * Test if the current string refers to a month
     */
    public boolean test_month(String tok) {
        return MONTHS.contains(tok);
    }

    /**
     * Test if the current string refers to country code
     */
    public boolean test_country_codes(String tok) {
        return COUNTRY_CODES.contains(tok);
    }

    /**
     * Test if the current string refers to a kind code
     */
    public boolean test_kind_codes(String tok) {
        return KIND_CODES.contains(tok);
    }

    /**
     * Test if the current string refers to a country
     */
    public boolean test_country(String tok) {
        return lexicon.isCountry(tok.toLowerCase());
    }

	/**
     * Test if the current string refers to a known city
     */
    public boolean test_city(String tok) {
        List<OffsetPosition> pos = lexicon.inCityNames(tok.toLowerCase());
		if ((pos != null) && (pos.size() > 0) )
			return true;
		else 
			return false;
    }

    /**
     * Give the relative position base on charcater or token numbers discretized into nbBins
     */
    public int relativeLocation(int pos, int total, int nbBins) {
        float rel = (float) pos / total;
        float rel2 = (rel * nbBins) + 1;
        return ((int) rel2);
    }
	
    /**
     * Give the relative position based on layout coordinates discretized into nbBins
     */
    public int relativeLocation(double pos, double total, int nbBins) {
        double rel = pos / total;
        double rel2 = (rel * nbBins) + 1;
        return ((int) rel2);
    }
}