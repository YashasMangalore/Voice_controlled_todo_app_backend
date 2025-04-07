package com.todo.voice.analyzers;

import com.todo.voice.util.StemmerHelper;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.Set;

public class MyAnalyzer extends Analyzer {
    private final CharArraySet stopWords;
    private final Set<String> protectedWords;
    public MyAnalyzer(CharArraySet stopWords, Set<String> protectedWords) {
        this.stopWords = stopWords;
        this.protectedWords = protectedWords;
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer();
        TokenStream tokenStream=new StopFilter(tokenizer,stopWords);
        tokenStream=new TokenFilter(tokenStream) {
            @Override
            public boolean incrementToken() throws IOException {
                if(!input.incrementToken()) {
                    return false;
                }
                CharTermAttribute charTermAttribute=getAttribute(CharTermAttribute.class);
                String term=charTermAttribute.toString();
                return !protectedWords.contains(term);
            }
        };
        tokenStream=new PorterStemFilter(tokenStream);
        return new TokenStreamComponents(tokenizer,tokenStream);
    }

    public String stem(String text){
        if(text==null|| text.isBlank()){
            return text;
        }
        Pair<String,String> placeholders= StemmerHelper.getPlaceholders(text,protectedWords);
        text= placeholders.getFirst();
        try(TokenStream tokenStream=tokenStream(null,text)){
            StringBuilder result=new StringBuilder();
            CharTermAttribute charTermAttribute=tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while(tokenStream.incrementToken()){
                result.append(charTermAttribute.toString()).append(" ");
            }
            tokenStream.end();
            String stemmedText=result.toString();
            stemmedText = stemmedText.replace("_PLACEHOLDER_", placeholders.getSecond());
            return stemmedText.trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
