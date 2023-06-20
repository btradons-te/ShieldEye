package com.cisco.te.shieldeye.sdavc.client;

import com.cisco.te.shieldeye.sdavc.client.model.DetectedAnomaly;

public class WeakCred {
    String weakUserName;
    String weakPassword;
    public WeakCred(String weakUserName, String weakPassword){
        this.weakUserName = weakUserName;
        this.weakPassword = weakPassword;
    }

    public WeakCred(DetectedAnomaly detectedAnomaly, boolean showSensitive){
        this.weakUserName = detectedAnomaly.getAnomalyProbes().get(0).getWeakUserName().get(0);
        this.weakPassword = detectedAnomaly.getAnomalyProbes().get(0).getWeakPassword().get(0);
        if (!showSensitive){
//            this.weakUserName = this.weakUserName.replaceAll("\\B\\w\\B", "*");
//            this.weakPassword = this.weakPassword.replaceAll("\\B\\w\\B", "*");
            String tmp = this.weakUserName.substring(0,1);
            for (int i=1; i<weakUserName.length();i++){
                tmp += "*";
            }
            this.weakUserName =tmp;

            tmp = this.weakPassword.substring(0,1);
            for (int i=1; i<weakPassword.length();i++){
                tmp += "*";
            }
            this.weakPassword =tmp;
        }
    }

    public String toString(){
        return "weakUsername: " + this.weakUserName + " weakPassword: "+ this.weakPassword;
    }

}
