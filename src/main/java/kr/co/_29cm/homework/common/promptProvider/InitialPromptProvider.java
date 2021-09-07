package kr.co._29cm.homework.common.promptProvider;

import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;




@Component
public class InitialPromptProvider implements PromptProvider{

    private final String INITIAL_PROMPT = "입력(o[order]: , q[quit]: )";


    @Autowired
    private LineReader reader;



    @Override
    public AttributedString getPrompt() {
        String prompt = INITIAL_PROMPT;
        return new AttributedString(prompt);
    }

    
    public String ask(String question) {
        return this.reader.readLine("\n" + question );
    }

    
}
