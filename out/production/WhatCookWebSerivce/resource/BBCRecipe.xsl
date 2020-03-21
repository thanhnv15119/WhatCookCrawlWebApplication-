<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="/">
        <xsl:element name="recipe">
            <xsl:element name="name">
                <xsl:value-of
                        select="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class,recipe-header__details-first)]/h1/text()"/>
            </xsl:element>
            <xsl:element name="image">
                <xsl:value-of
                        select="/div/article/header/div[1]/div/img/@src"/>
            </xsl:element>


            <xsl:element name="yield">
                <xsl:value-of
                        select="/div/article/header/div[contains(@class,'recipe-header__details')]/div[contains(@class,'recipe-header__details-second')]/div/section[contains(@class,'recipe-details__item recipe-details__item--servings')]/span/text()"/>
            </xsl:element>

            <xsl:element name="nutrition">
                <xsl:element name="calories">

                    <xsl:variable name="calories"
                                  select="/div/article/header/div[contains(@class,'recipe-header__bottom')]/div[contains(@class,'recipe-header__bottom-right')]/section/div/div/div/ul/li[1]/span[2]/text()"/>
                    <xsl:value-of select="translate($calories, 'g', '')"/>
                </xsl:element>
                <xsl:element name="carbs">
                    <xsl:variable name="calories"
                                  select="/div/article/header/div[contains(@class,'recipe-header__bottom')]/div[contains(@class,'recipe-header__bottom-right')]/section/div/div/div/ul/li[4]/span[2]/text()"/>
                    <xsl:value-of select="translate($calories, 'g', '')"/>
                </xsl:element>
                <xsl:element name="fat">
                    <xsl:variable name="calories"
                                  select="/div/article/header/div[contains(@class,'recipe-header__bottom')]/div[contains(@class,'recipe-header__bottom-right')]/section/div/div/div/ul/li[2]/span[2]/text()"/>
                    <xsl:value-of select="translate($calories, 'g', '')"/>
                </xsl:element>
                <xsl:element name="protein">
                    <xsl:variable name="calories"
                                  select="/div/article/header/div[contains(@class,'recipe-header__bottom')]/div[contains(@class,'recipe-header__bottom-right')]/section/div/div/div/ul/li[7]/span[2]/text()"/>
                    <xsl:value-of select="translate($calories, 'g', '')"/>
                </xsl:element>
                <xsl:element name="fiber">
                    <xsl:variable name="calories"
                                  select="/div/article/header/div[contains(@class,'recipe-header__bottom')]/div[contains(@class,'recipe-header__bottom-right')]/section/div/div/div/ul/li[6]/span[2]/text()"/>
                    <xsl:value-of select="translate($calories, 'g', '')"/>
                </xsl:element>
            </xsl:element>

            <xsl:element name="ingredients">
                <xsl:for-each
                        select="/div/article/div/div/div[contains(@class,'recipe-content')]/div[@id='responsive-tabs']/div/section[@id='recipe-ingredients']/div/div/ul/li">
                    <xsl:element name="ingredient">
                        <xsl:choose>
                            <xsl:when test="a/text() != ''">
                                <xsl:value-of select="text()"/>&#160;<xsl:value-of select="a/text()"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="text()"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>

            <xsl:element name="methods">
                <xsl:variable name="count" select="0"/>
                <xsl:for-each
                        select="/div/article/div/div/div[contains(@class,'recipe-content')]/div[@id='responsive-tabs']/div/section[@id='recipe-method']/div/ol/li">
                    <xsl:element name="method">
                        <xsl:element name="step">
                            <xsl:value-of select="position()"/>
                        </xsl:element>
                        <xsl:element name="content">
                        <xsl:choose>
                            <xsl:when test="p/a/text() != ''">
                                <xsl:value-of select="p"/> <xsl:value-of select="p/a/text()"/>&#13;
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="p/text()"/>&#13;
                            </xsl:otherwise>
                        </xsl:choose>
                        </xsl:element>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="prepTime">
                <xsl:choose>
                    <xsl:when
                            test="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-prep')]">
                        <xsl:choose>
                            <xsl:when
                                    test="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-prep')]/span[contains(@class,'mins')]">
                                <xsl:variable name="time"
                                              select="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-prep')]/span[contains(@class,'mins')]/text()"/>
                                <xsl:value-of select="translate($time, 'mins','')"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="time"
                                              select="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-prep')]/span[contains(@class,'hrs')]/text()"/>
                                <xsl:value-of select="translate($time, 'hrs','') * 60"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                </xsl:choose>
            </xsl:element>

            <xsl:element name="cookTime">
                <xsl:choose>
                    <xsl:when
                            test="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-cook')]">
                        <xsl:choose>

                            <xsl:when
                                    test="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-cook')]/span[contains(@class,'mins')]">
                                <xsl:variable name="time"
                                              select="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-cook')]/span[contains(@class,'mins')]/text()"/>
                                <xsl:value-of select="translate($time, 'mins','')"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="time"
                                              select="/div/article/header/div[contains(@class, 'recipe-header__details')]/div[contains(@class, 'recipe-header__details-second')]/div/section[contains(@class, 'recipe-details__item recipe-details__item--cooking-time')]/div/span[contains(@class,'recipe-details__cooking-time-cook')]/span[contains(@class,'hrs')]/text()"/>
                                <xsl:value-of select="translate($time, 'hrs','') * 60"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                </xsl:choose>
            </xsl:element>


        </xsl:element>
    </xsl:template>
</xsl:stylesheet>