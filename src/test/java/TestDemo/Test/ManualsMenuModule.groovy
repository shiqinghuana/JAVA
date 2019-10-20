package TestDemo.Test
import geb.Module


class ManualsMenuModule extends Module {                                 //1
    static content = {                                                   //2
        toggle { $("div.menu a.manuals") }
        linksContainer { $("#manuals-menu") }
        links { linksContainer.find("a") }                               //3
    }

    void open() {                                                        //4
        toggle.click()
        waitFor { !linksContainer.hasClass("animating") }
    }
}


