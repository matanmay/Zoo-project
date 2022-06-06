package Utils;
import java.util.*;

/**
 * Created by Antonio Zaitoun on 31/08/2017.
 */
public class CSVExportRequest{

    private String name;
    private String[] columns;
    private List<String[]> data;

    public CSVExportRequest(String fileName,Collection<String[]> data,String[] columns){
        setData(new ArrayList<>(data));
        setColumns(Arrays.copyOf(columns,columns.length));
        setName(fileName);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setColumns(String[] columns) {
        this.columns = columns;
    }

    private void setData(List<String[]> data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String[] getColumns() {
        return columns;
    }

    public List<String[]> getData() {
        return Collections.unmodifiableList(data);
    }
}
