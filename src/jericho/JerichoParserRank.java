package jericho;

import java.util.ArrayList;
import java.util.List;

import fr.sport.rinkoid.ranks.Rank;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class JerichoParserRank {

    public JerichoParserRank() {
        MicrosoftConditionalCommentTagTypes.deregister();
        PHPTagTypes.deregister();
        MasonTagTypes.deregister();
    }

    public void Parse(String input, ArrayList<Rank> ranks) {
        Source source = new Source(input);
        source.fullSequentialParse();
        int index = 1;
        List<Element> elements = source.getAllElements("tr");
        for(int i = 0; i < elements.size(); ++i) {
            StartTag tag =  elements.get(i).getStartTag();
            if( "separate".equalsIgnoreCase(tag.getAttributeValue("class"))
                || "odd separate".equalsIgnoreCase(tag.getAttributeValue("class"))
                || "odd ".equalsIgnoreCase(tag.getAttributeValue("class"))
                || "".equalsIgnoreCase(tag.getAttributeValue("class"))) {

                String team = "";
                int rank = 0, points = 0, day = 0, win = 0, draw = 0,
                        lost = 0, goalFor = 0, goalAnti = 0;
                List<Element> children = elements.get( i ).getAllElements("td");
                for(int j = 0; j < children.size(); ++j) {
                    switch( j ) {
                    case 0: rank = ParseInteger(children.get(j), index); break;
                    case 1: team = ParseString(children.get(j)); break;
                    case 2: points = ParseInteger(children.get(j)); break;
                    case 3: day = Integer.parseInt(children.get(j).
                            getContent().toString()); break;
                    case 4: win = Integer.parseInt(children.get(j).
                            getContent().toString()); break;
                    case 5: draw = Integer.parseInt( children.get(j).
                            getContent().toString()); break;
                    case 6: lost = Integer.parseInt(children.get(j).
                            getContent().toString()); break;
                    case 8: goalFor = Integer.parseInt(children.get(j).
                            getContent().toString()); break;
                    case 9: goalAnti = Integer.parseInt(children.get(j).
                            getContent().toString()); break;
                        default:
                    }
                }
                index = rank;
                ranks.add(new Rank(team, points, win, draw, lost,
                        goalFor-goalAnti));
            }
        }
    }

    private int ParseInteger(Element element) {
        int rank = 0;
        List<Element> elements = element.getAllElements("strong");
        for(int i = 0; i < elements.size(); ++i)
            rank = Integer.parseInt(elements.get(i).getContent().toString());
        return rank;
    }

    private int ParseInteger(Element element, int index) {
        List< Element > elements = element.getAllElements("strong");
        if(elements.size() > 0)
            return ParseInteger(element);
        else
            return index + 1;
    }

    private String ParseString(Element element) {
        String result = "";
        List<Element> elements = element.getAllElements("a");
        for(int i = 0; i < elements.size(); ++i)
            result = elements.get(i).getContent().toString();
        return result;
    }
}
