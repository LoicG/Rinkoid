package jericho;

import java.util.ArrayList;
import java.util.List;

import fr.sport.rinkoid.shedule.Match;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftConditionalCommentTagTypes;
import net.htmlparser.jericho.PHPTagTypes;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class JerichoParserFixture
{
    public JerichoParserFixture() {
        MicrosoftConditionalCommentTagTypes.deregister();
        PHPTagTypes.deregister();
        MasonTagTypes.deregister();
    }

    private String ParseFixture( Element element ) {
        String result = "";
        List<Element> children = element.getAllElements("a");
        for(int i = 0; i < children.size(); ++i) {
            List<Element> list = element.getAllElements("span");
            if(list.size() == 0)
                result += children.get(i).getContent().toString();
            else for(int j = 0; j < children.size(); ++j)
                    result += list.get(j).getContent().toString();
        }
        return result;
    }

    private String ParseScore(Element element) {
        String result = "";
        List<Element> children = element.getAllElements("a");
        if(children.size() == 0)
            result += element.getContent().toString();
        else {
            List<Element> divChildren = element.getAllElements("div");
            if(!divChildren.isEmpty())
                result += divChildren.get(divChildren.size()-1).getContent().toString();
        }
        return result.replaceAll("&nbsp;", "");
    }

    private String Parse(Element element, String index) {
        String result = "";
        List<Element> children = element.getAllElements(index);
        for(int i = 0; i < children.size(); ++i)
            result += children.get(i).getContent().toString();
        return result;
    }

    public void Parse(String input, ArrayList<Match> matchs) {
        String homeTeam = "unknown", outTeam = "unknown", score = "unknown",
               date = "unknown";
        Source source = new Source(input);
        source.fullSequentialParse();
        List<Element> elements = source.getAllElements();
        for(int i = 0; i < elements.size(); ++i ) {
            StartTag tag =  elements.get( i ).getStartTag();
            if(tag.getName() == "table" && "resultat".equalsIgnoreCase(
                    tag.getAttributeValue("class")))
                date = Parse(elements.get(i), "caption");
            if(tag.getName() == "td" && "right".equalsIgnoreCase(
                    tag.getAttributeValue("class")))
                homeTeam = ParseFixture(elements.get(i));
            if(tag.getName() == "td" && "center score".equalsIgnoreCase(
                    tag.getAttributeValue("class")))
                score = ParseScore(elements.get(i));
            if(tag.getName() == "td" && "left".equalsIgnoreCase(
                    tag.getAttributeValue("class")))
                outTeam = ParseFixture(elements.get(i) );
            if(homeTeam != "unknown" && outTeam != "unknown" && score != "unknown")
            {
                matchs.add(new Match(homeTeam, score, outTeam, date));
                homeTeam = "unknown";
                outTeam = "unknown";
                score = "unknown";
            }
        }
    }
}
