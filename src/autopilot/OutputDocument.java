package autopilot;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class OutputDocument{

    private Section parentSection = null;

    //nextName counter
    private char nextName = 'A';

    //sections as map
    private Map<String,Section> sections = new TreeMap<>();

    /**
     * Create a new section.
     * @return The newly created section.
     */
    public Section nextSection(){
        String parentName = parentSection == null ? null : parentSection.getName();
        Section section = new Section(String.valueOf(nextName));
        section.setParent(parentName);
        sections.put(String.valueOf(nextName++),section);
        return section;
    }

    Section nextSection(String name){
        String parentName = parentSection == null ? null : parentSection.getName();
        Section section = new Section(name);
        section.setParent(parentName);
        sections.put(name,section);
        return section;
    }

    /**
     * Get existing section by letter.
     * @param name The nextName of the section (char)
     * @return The section if exists else null.
     */
    public Section existingSection(String name){
        return sections.get(name);
    }

    /**
     * Get the result in an XML format. This format should be easily parsed.
     * @return The value of all the sections
     */
    public String exportSections(){
        return exportSections(null,null,true);
    }

    public String exportNormal() {
        return exportSections(null,null,false);
    }

    public Map<String,Section> sections(){
        return Collections.unmodifiableMap(sections);
    }


    public String exportSections(String documentName,String parentName,boolean xml){
        StringBuilder builder = new StringBuilder();

        if (documentName == null)
            documentName = getDirName();

        //append header
        builder
                .append(xml ? "<Document name='" : "")
                .append(xml ? documentName: "")
                .append(xml ? "' count='" : "")
                .append(xml ? sections.size() : "")
                .append(xml ? "'>\n" : "");

        //append data
        for (Map.Entry<String,Section> entry : sections.entrySet()) {
            //add section
            builder.append(xml ? "<Section name='" : "")
                    .append(xml ? parentName == null ? "" : parentName + "_" : "")
                    .append(xml ? entry.getKey() : "")
                    .append(xml ? "'>\n" : "");
            //add data to section
            builder.append(xml ? "<data>\n" : "");
            builder.append(entry.getValue().result());
            builder.append(xml ? "</data>\n" : "\n");

            //add sub document if exists
            builder.append(entry.getValue().documentOutput(xml));

            builder.append(xml ? "</Section>\n" : "");
        }

        //close document
        builder.append(xml ? "</Document>\n" : "");
        return builder.toString();
    }

    /**
     * Helper method used to get the project's dir nextName.
     * @return the project's dir nextName.
     */
    private String getDirName(){
        String[] path = System.getProperty("user.dir").split(Pattern.quote(File.separator));
        return path.length > 0 ? path[path.length-1] : "unknown_project";
    }

    double getScore(){
        double sum = 0;
        for (Section section : sections.values()) {
            sum += section.getScore(false);
        }
        return sum;
    }

    public boolean isEmpty(){
        for (Section section : sections.values()) {
            if(!section.isEmpty())
                return false;
        }
        return true;
    }

    public int numberOfSections(){
        return sections.size();
    }


    public Map<String,Section> flatSections(){
        List<Section> sections = new ArrayList<>(this.sections.values());

        for (Section section : this.sections.values()) {
            OutputDocument document = section.getDocument();
            if(document != null) {
                sections.addAll(document.flatSections().values());
            }
        }

        Map<String,Section> map = new HashMap<>();
        for (Section section : sections)
            map.put(section.getName(),section);


        return map;
    }

    public void setParentSection(Section parentSection) {
        this.parentSection = parentSection;
        for (Section section : sections.values()) {
            section.setParent(parentSection.getName());
        }
    }
}
