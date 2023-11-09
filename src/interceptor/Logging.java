package interceptor;

import model.enums.LogTypes;
import util.FileUtil;
import util.impl.FileUtility;

public class Logging {
private final FileUtil fileUtil = new FileUtility();
public void info(LogTypes logTypes, String logMessage) {
        fileUtil.writeFile(logTypes.getFileName(), logMessage);
        }
        }

