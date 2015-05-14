/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date_transform;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Danya
 */
public class Date_transform {
    public static final Charset USED_CHAR_SET = Charset.forName("US-ASCII");
    public static final Path FROM_STORAGE_PATH = Paths.get("C:\\Users\\Danya\\Documents\\BD\\mysql"); 
    public static final Path TO_STORAGE_PATH = Paths.get("conv_storage"); 
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
            try{
                String filename = "dep_gen.sql";
                boolean full = false;
                
                byte[] encoded = Files.readAllBytes(Paths.get(FROM_STORAGE_PATH.toString() + "/" + filename));
                String outfbuf = new String(encoded, USED_CHAR_SET);
                
                //if((n=outFile.read(outfbuf))>0){
                    Scanner s = new Scanner(outfbuf);
                    FileWriter wr = new FileWriter(TO_STORAGE_PATH.toString() + "/" + filename);
                    
                    /*Pattern p = Pattern.compile(Pattern.quote("To:") + "(.*?)" + Pattern.quote("Subject:"));
                    Matcher m = p.matcher(outfbuf);*/
                    
                    /*List<String> strings = Arrays.asList( outfbuf.replaceAll("^.*?Subject:", "").split("To:.*?(Subject:|$)"));
                    sTrace.debug("!!!!!!!!!!"+strings.get(0));*/
                    
                    String yyyy = "";
                    String mm = "";
                    String dd = "";
                    
                    String same_data = "";
                    
                    wr.write("");
                    
                    //if(m.find()){
                    //    sTrace.debug("!!!!We found: " + m.group(1));
                    
                    StringTokenizer st = new StringTokenizer(/*m.group(1)*/outfbuf,"'");
                    
                        while(st.hasMoreTokens()){
                            same_data = st.nextToken("'");
                            wr.append(same_data);
                            
                            if(st.hasMoreTokens()){
                                dd=st.nextToken("-").replace("'", "");
                                //System.out.println("rt: "+dd);
                                mm=st.nextToken("-").replace("-", "");
                                //System.out.println("rt: "+mm);
                                if(!full){
                                    yyyy=st.nextToken("'").replace("-", "");
                                    wr.append("'"+yyyy+"-"+month_convertion(mm)+"-"+dd+"'");
                                }
                                else{
                                    yyyy=st.nextToken(" ").replace("-", "");
                                    wr.append("'"+yyyy+"-"+month_convertion(mm)+"-"+dd+" ");
                                    wr.append(st.nextToken("'")+"'");
                                    wr.append(st.nextToken("'")+"'");
                                    wr.append(st.nextToken("'")+"'");
                                }
                                //System.out.println("rt: "+yyyy);
                                
                                System.out.println("Written: "+"'"+yyyy+"-"+mm+"-"+dd+"'");
                            }
                        }
                        
                    //} else 
                    
                    /*Scanner finder = new Scanner(field.group(1));
                        finder.findInLine("<(\\w+)@");
                        MatchResult result = finder.match();
                        for (int i=1; i<=result.groupCount(); i++){
                            sTrace.debug("!!!!!!!!!!"+result.group(i));
                            if(filename.equalsIgnoreCase(result.group(i)))
                                found = true;
                        }
                        finder.close();*/
                    
                    s.close(); 
                    wr.close();
                    
                //}
                //outFile.close();
            } catch (IOException e) {
                System.err.println("Error acquiring file: " + e.getMessage());
            } 
    }
    
    public static String month_convertion(String in_month){
        String out_month = "";
        switch (in_month){
            case "JAN": out_month = "01"; break;
            case "FEB": out_month = "02"; break;
            case "MAR": out_month = "03"; break;
            case "APR": out_month = "04"; break;
            case "MAY": out_month = "05"; break;
            case "JUN": out_month = "06"; break;
            case "JUL": out_month = "07"; break;
            case "AUG": out_month = "08"; break;
            case "SEP": out_month = "09"; break;
            case "OCT": out_month = "10"; break;
            case "NOV": out_month = "11"; break;
            case "DEC": out_month = "12"; break;
            default: out_month = "00"; break;
        }
        return out_month;
    }
}
