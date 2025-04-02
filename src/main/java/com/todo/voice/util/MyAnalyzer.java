package com.todo.voice.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import javax.swing.text.AttributeSet;
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
        tokenStream=new PorterStemFilter(tokenStream);
        return new TokenStreamComponents(tokenizer,tokenStream);
    }

    public String stem(String text){
        if(text==null|| text.isBlank()){
            return null;
        }
        StringBuilder result=new StringBuilder();
        try(TokenStream tokenStream=tokenStream(null,text)){
            CharTermAttribute charTermAttribute=tokenStream.getAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while(tokenStream.incrementToken()){
                result.append(charTermAttribute.toString()).append(" ");
            }
            tokenStream.end();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result.toString();
    }
}
