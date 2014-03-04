package jericho;

import java.util.ArrayList;
import java.util.List;

import fr.sport.rinkoid.kickers.Kicker;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class JerichoParserKicker
{
    public JerichoParserKicker() {
        MicrosoftConditionalCommentTagTypes.deregister();
        PHPTagTypes.deregister();
        MasonTagTypes.deregister();
    }

    public void Parse(String input, ArrayList< Kicker > kickers) {
        Source source = new Source(input);
        source.fullSequentialParse();
        int index = 1;
        List< Element > elements = source.getAllElements("tr");
        for(int i = 0; i < elements.size(); ++i) {
            StartTag tag =  elements.get(i).getStartTag();
            if( "separate".equalsIgnoreCase(tag.getAttributeValue("class")) 
                || "odd separate".equalsIgnoreCase(tag.getAttributeValue("class"))
                || "odd ".equalsIgnoreCase(tag.getAttributeValue("class"))
                || "".equalsIgnoreCase(tag.getAttributeValue("class"))) {
                int rank = 0, goals = 0;
                String name = "", team = "";
                List< Element > children = elements.get(i).getAllElements("td");
                for(int j = 0; j < children.size(); ++j) {
                    switch( j ) {
                        case 0: rank = ParseInteger(children.get(j), index);
                                break;
                        case 1: name = ParseString(children.get(j));
                                break;
                        case 2: team = ParseString(children.get( j ));
                                break;
                        case 4: goals = ParseInteger(children.get(j), index);
                                break;
                        default:
                    }
                }
                index = rank;
                kickers.add(new Kicker(name, goals, team));
            }
        }
    }

    private int ParseInteger(Element element, int index) {
        int rank = 0;
        List<Element> elements = element.getAllElements("strong");
        if(elements.size() > 0)
            for(int i = 0; i < elements.size(); ++i)
                rank = Integer.parseInt( elements.get(i).getContent().toString());
        else
           rank = element.getContent().toString().equals("") ? index :
               Integer.parseInt(element.getContent().toString());
        return rank;
    }


    private String ParseString(Element element) {
        String result = "";
        List<Element> elements = element.getAllElements("a");
        for(int i = 0; i < elements.size(); ++i)
            result = elements.get(i).getContent().toString();
        return result;
    }
}
