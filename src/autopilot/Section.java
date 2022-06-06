package autopilot;

import java.util.regex.Pattern;

public class Section {

    private String parent = null;

    private final String name;

    /**
     * binary means that the section it self is binary comparable.
     * If a section is binary, then the results may show up or may not show up.
     * When a section is not binary, it indicates that this section has a fixed-length and will always contain the same amount of results.
     *
     * Important:
     * If the section is binary, it is important to make sure that the lines DO NOT repeat themselves.
     */
    private boolean binary = false;

    /**
     * How much score this document receives in comparison to other sections in the same document.
     */
    private double score = 0.0;

    private StringBuilder builder = new StringBuilder();

    private OutputDocument document = null;

    Section(String name) {
        this.name = name;
    }

    public void write(Object o){
        builder.append(String.valueOf(o)).append("\n");
    }

    public String result(){
        //CONTENT +
        //DOCUMENT OUTPUT
        return builder.toString();
    }

    boolean isBinary() {
        return binary;
    }

    void setBinary(boolean binary) {
        this.binary = binary;
    }

    double getScore(boolean flat) {
        return score + (flat || document == null ? 0 : document.getScore());
    }

    void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return parent == null ? name : parent + "_" + name;
    }

    public OutputDocument document() {
        if (document == null) {
            document = new OutputDocument();
            document.setParentSection(this);
        }
        return document;
    }

    OutputDocument getDocument(){
        return document;
    }

    String documentOutput(boolean xml) {
        return document == null ? "" : document.exportSections("sub",String.valueOf(name),xml);
    }



    public String[] getSectionData() { return builder.toString().split(Pattern.quote("\n")); }

    public boolean isDocumentEmpty(){
        return document == null || document.isEmpty();
    }

    public boolean isEmpty(){
        return builder.length() == 0 && isDocumentEmpty();
    }

    public int documentSize() {
        return document == null ? 0 : document.numberOfSections();
    }

    void setDocument(OutputDocument document){
        this.document = document;
        document.setParentSection(this);
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
