package org.example;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
public class TextToSpeech {
    public static void main(String[] args) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        Voice[] voicelist = VoiceManager.getInstance().getVoices();
        for(int  i = 0 ; i  <voicelist.length ; i++) {
            System.out.println("voice :" + voicelist[i].getName());
        }
        if(voice!=null) {
            voice.allocate();
            System.out.println("Voice Rate: " + voice.getRate());
            System.out.println("Voice pitch: " + voice.getPitch());
            System.out.println("Voice Volume: " + voice.getVolume());
            boolean status  = voice.speak("Long time no see");
            System.out.println("status " + statuc);
            voice.deallocate();
        }
    }
}
