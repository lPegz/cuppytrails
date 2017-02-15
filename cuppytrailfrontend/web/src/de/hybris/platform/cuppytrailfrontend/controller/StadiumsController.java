package de.hybris.platform.cuppytrailfrontend.controller;

import com.sap.security.core.server.csi.util.URLDecoder;
import de.hybris.platform.cuppytrail.facades.StadiumFacade;
import de.hybris.platform.cuppytrailfrontend.StadiumsNameEncoded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * StadiumsController controlling /stadiums and /stadiums/{stadiumName} routes
 */
@Controller
public class StadiumsController {

    private StadiumFacade stadiumFacade;

    @RequestMapping("/stadiums")
    public String showStadiums(final Model model) {

        model.addAttribute("stadiums", stadiumFacade.getStadiums());
        return "StadiumListing";
    }

    @RequestMapping("/stadiums/{stadiumName}")
    public String showStadiumDetails(@PathVariable String stadiumName, final Model model) {
        stadiumName = URLDecoder.decode(stadiumName, "UTF-8");
        model.addAttribute( "stadium",
                stadiumFacade.getStadiumForCode(StadiumsNameEncoded.getNameEncoded(stadiumName)));
        return "StadiumDetails";
    }

    @Autowired
    public void setStadiumFacade(StadiumFacade stadiumFacade) {
        this.stadiumFacade = stadiumFacade;
    }
}
