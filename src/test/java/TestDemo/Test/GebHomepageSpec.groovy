package TestDemo.Test




class GebHomepageSpec{

    def "can access The Book of Geb via homepage"() {
        given:
        to GebHomePage

        when:
        GebHomePage.manualsMenu.open()
        GebHomePage.manualsMenu.links[0].click()

        then:
        at TheBookOfGebPage
    }


}