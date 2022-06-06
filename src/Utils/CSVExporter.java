package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Antonio Zaitoun on 31/08/2017.
 */
final public class CSVExporter {

    private final static String FILE_SEPERATOR = ",";

    private final static String NEW_LINE = "\n";

    private final static String QUOTE = "\"";

    private final static String EXT = ".csv";

    /**
     * Export a CSV request synchronously from raw data.
     *
     * @param fileName The name of the file (not including .csv)
     * @param data The data to export.
     * @param columns The column names.
     */
    public static void export(String fileName, Collection<String[]> data,String[] columns){
        export(new CSVExportRequest(fileName,data,columns));
    }

    /**
     * Export a CSV using a CSVExportRequest Synchronously.
     *
     * @param request The object containing the data to export.
     */
    public static void export(CSVExportRequest request) {
        export(request,null);
    }

    /**
     * Export a CSV using a CSVExportRequest Synchronously.
     *
     * @param request The object containing the data to export.
     * @param callBack The callback function that is called when the export is finished.
     */
    public static void export( CSVExportRequest request,ExportCallBack callBack){
        export(request,callBack,false);
    }

    /**
     * Export a CSV using a CSV Export Request
     *
     * @param request The object containing the data to export.
     * @param callBack The callback function that is called when the export is finished.
     * @param async set to true to run in a background thread, and false for synchronized usage.
     */
    public static void export( CSVExportRequest request,  ExportCallBack callBack, boolean async){
        Thread s = new Thread(() -> {
            try {
                PrintWriter writer = new PrintWriter(request.getName() + EXT,"UTF-8");

                final int columns = request.getColumns().length;

                //write columns
                for(int i = 0; i < columns; i++ )
                    writer.print(QUOTE + request.getColumns()[i] + QUOTE +
                            (i == columns - 1 ? NEW_LINE : FILE_SEPERATOR));

                //write data
                for(int i = 0; i < request.getData().size();i++)
                    for(int j = 0; j < columns ; j++)
                        writer.print(QUOTE + request.getData().get(i)[j] + QUOTE +
                                (j == columns - 1 ? NEW_LINE : FILE_SEPERATOR));

                writer.close();

                if(callBack != null)
                    callBack.callBack(request.getName() + EXT,true);

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                if(callBack != null)
                    callBack.callBack(request.getName() + EXT,false);
            }
        });

        if(async)
            s.start();
        else
            s.run();

    }

    /**
     * Import a CSV file as {@code ArrayList<String[]>}
     *
     * @param path The path to file to import.
     * @return The data as an Array List of generic type String[].
     * @throws IOException
     */
    public static List<String[]> importCSV(String path) throws IOException {
        ArrayList<String[]> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));

        String line;
        while ((line = br.readLine()) != null) {
            String[] entries = line.split(FILE_SEPERATOR);
            list.add(entries);
        }

        return list;
    }

    @FunctionalInterface
    public interface ExportCallBack{
        /**
         * @param fileName The name of the file including the `csv` extension.
         * @param success true if exported successfully.
         */
        void callBack(String fileName, boolean success);
    }
}
