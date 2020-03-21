<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="/">
        <xsl:element name="recipe">
            <xsl:element name="name">
                <xsl:value-of
                        select="/div/div/noscript/div/div/h2/text()"/>
            </xsl:element>
            <xsl:element name="image">
                <xsl:value-of
                        select="/div/div/noscript/div/div/div[1]/img/@src"/>
            </xsl:element>

            <xsl:element name="yield">
                <xsl:value-of
                        select="/div/div/noscript/div/div/p/span[contains(@itemprop, 'recipeYield')]/text()"/>
            </xsl:element>

            <xsl:element name="nutrition">
                <xsl:element name="calories">

                    <xsl:value-of
                            select="/div/div/noscript/div/div/div[contains(@itemprop,'nutrition')]/span[contains(@itemprop,'calories')]/text()"/>
                </xsl:element>
                <xsl:element name="carbs">
                    <xsl:value-of
                            select="/div/div/noscript/div/div/div[contains(@itemprop,'nutrition')]/span[contains(@itemprop,'carbohydrateContent')]/text()"/>
                </xsl:element>
                <xsl:element name="fat">
                    <xsl:value-of
                            select="/div/div/noscript/div/div/div[contains(@itemprop,'nutrition')]/span[contains(@itemprop,'fatContent')]/text()"/>
                </xsl:element>
                <xsl:element name="protein">
                    <xsl:value-of
                            select="/div/div/noscript/div/div/div[contains(@itemprop,'nutrition')]/span[contains(@itemprop,'proteinContent')]/text()"/>
                </xsl:element>
                <xsl:element name="fiber">
                    <xsl:value-of
                            select="/div/div/noscript/div/div/div[contains(@itemprop,'nutrition')]/span[contains(@itemprop,'fiberContent')]/text()"/>
                </xsl:element>
            </xsl:element>

            <xsl:element name="ingredients">
                <xsl:for-each select="/div/div/noscript/div/div/div[2]/span">
                    <xsl:element name="ingredient">
                        <xsl:value-of select="text()"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>

        </xsl:element>
    </xsl:template>
</xsl:stylesheet>