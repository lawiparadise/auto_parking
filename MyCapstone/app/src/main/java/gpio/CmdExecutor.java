//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gpio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdExecutor {
    public CmdExecutor() {
    }

    protected static String execute(String[] cmd) {
        StringBuffer output = new StringBuffer();

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader e = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while((line = e.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (InterruptedException | IOException var5) {
            ;
        }

        return output.toString();
    }

    protected static String execute(String cmd) {
        StringBuffer output = new StringBuffer();

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            BufferedReader e = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = "";

            while((line = e.readLine()) != null) {
                output.append(line).append("\n");
            }

            String errLine = "";

            while((errLine = err.readLine()) != null) {
                output.append(errLine).append("\n");
            }
        } catch (InterruptedException | IOException var7) {
            ;
        }

        return output.toString();
    }
}
