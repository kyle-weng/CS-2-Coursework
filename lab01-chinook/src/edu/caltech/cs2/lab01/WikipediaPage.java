package edu.caltech.cs2.lab01;

import edu.caltech.cs2.lab01.libraries.Wikipedia;

public class WikipediaPage {

    private String title;
    private String text;

    public WikipediaPage(String title, boolean followRedirects) {
        this.title = title;
        this.text = Wikipedia.getPageText(this.title);
        if (followRedirects && isRedirect()) {
            String redirect = Wikipedia.parseMarkup(text);
            this.text = Wikipedia.getPageText(redirect);
        }
    }

    public WikipediaPage(String title) {
        this(title, true);
    }

    // Capitalize first letter, everything else lowercase, replace underscores with spaces
    public String getTitle() {
        String str1 = title.replace('_', ' ');
        String str2 = convertToTitleCase(str1);
        return str2;
    }

    private static String convertToTitleCase(String s) {
        String[] split_strings = s.split(" ");
        String composite = "";
        for (String x : split_strings) {
            composite += (x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase() + " ");
        }
        return composite.trim();
    }

    public String getText() {
        return this.text;
    }

    public boolean isRedirect() {
        /*
        try {
            getText();
            return (getText().toLowerCase().startsWith("#redirect"));
        } catch (Exception e) {
            return false;
        }
        */
        if (isValid()) {
            return (getText().toLowerCase().startsWith("#redirect"));
        } else {
            return false;
        }
    }

    public boolean isValid() {
        //return (Wikipedia.isSpecialLink(title) || !(Wikipedia.getPageText(this.title) == null));
        return !(Wikipedia.getPageText(this.title) == null);
    }

    public boolean isGalaxy() {
        if (isValid()) {
            /*
            System.out.println(this.title);
            if (this.title == "Sunflower_Galaxy") {
                System.out.println(getText());
                return true;
            }
            */
            //return getText().toLowerCase().contains("infobox galaxy");
            return getText().toLowerCase().indexOf("infobox galaxy") != -1;

        }
        return false;
    }

    public boolean hasNextLink() {
        // Fill me in!
        return false;
    }

    public String nextLink() {
        // Fill me in!
        return null;
    }
}