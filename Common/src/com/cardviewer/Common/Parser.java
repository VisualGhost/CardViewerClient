package com.cardviewer.Common;

import com.cardviewer.Common.Lesson;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser
 */
public class Parser extends DefaultHandler {

    private Lesson mLesson;
    private boolean isInCard;
    private StringBuffer buf;

    private static final String LESSON = "lesson";
    private static final String CARD = "card";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String AUDIO = "audio";

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if (qName.equals(LESSON)) {
            mLesson = new Lesson();
            if (attributes != null) {
                mLesson.setLevelNumber(attributes.getValue(0));
                mLesson.setUnitNumber(attributes.getValue(1));
                mLesson.setUnitName(attributes.getValue(2));
                mLesson.setLessonNumber(attributes.getValue(3));
            }
        } else if (qName.equals(CARD)) {
            mLesson.createCard();
            isInCard = true;
        } else if (isInCard && qName.equals(TEXT)) {
            buf = new StringBuffer();
        } else if (isInCard && qName.equals(IMAGE)) {
            buf = new StringBuffer();
        } else if (isInCard && qName.equals(AUDIO)) {
            buf = new StringBuffer();
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        if (qName.equals(CARD)) {
            mLesson.addCardToCollection();
            isInCard = false;
        } else if (isInCard && qName.equals(TEXT)) {
            mLesson.setTextCard(buf.toString());
        } else if (isInCard && qName.equals(IMAGE)) {
            mLesson.setImageCard(buf.toString());
        } else if (isInCard && qName.equals(AUDIO)) {
            mLesson.setAudioCard(buf.toString());
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        readToBuf(buf, ch, start, length);
    }

    private void readToBuf(StringBuffer buf, final char[] ch, final int start, final int length) {
        if (buf != null) {
            for (int i = start; i < start + length; i++) {
                buf.append(ch[i]);
            }
        }
    }

    public Lesson getLesson() {
        return mLesson != null ? mLesson : (new Lesson());
    }
}
