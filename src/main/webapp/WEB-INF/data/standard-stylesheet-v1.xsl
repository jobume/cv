<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet version="1.0"
	xmlns:cv="http://www.sogeti.se/umea/curriculumvitae" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:variable name="borderColor">
		#f15c30
	</xsl:variable>
	<xsl:variable name="border">
		1px solid #f15c30
	</xsl:variable>

	<xsl:variable name="headlineFontSize">
		13px
	</xsl:variable>
	<xsl:variable name="fontSize">
		9px
	</xsl:variable>

	<xsl:variable name="portraitUrl">
		<xsl:value-of select="//cv:PortraitUrl" />
	</xsl:variable>

	<xsl:template name="tag_bold">
		<xsl:attribute name="font-weight">bold</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_uppercase">
		<xsl:attribute name="text-transform">uppercase</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_italic">
		<xsl:attribute name="font-style">italic</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_size_large">
		<xsl:attribute name="font-size">24px</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_size_medium">
		<xsl:attribute name="font-size">22px</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_size_small">
		<xsl:attribute name="font-size">16px</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_font_myriad">
		<!-- <xsl:attribute name="font-family">'Myriad Web Pro', 'Myriad Web', 'Myriad Pro', Frutiger, 'Frutiger Linotype', Univers, Calibri, 'Gill Sans', 'Gill Sans MT', , Myriad, 'DejaVu LGC Sans Condensed', 'DejaVu Sans Condensed', 'Liberation Sans', 'Nimbus Sans L', Tahoma, 'Helvetica Neue', Helvetica, Geneva,  Arial, sans-serif</xsl:attribute>  -->		
		<xsl:attribute name="font-family">'Myriad Pro', 'PT Sans', sans-serif</xsl:attribute>
	</xsl:template>

	<xsl:template name="tag_font_trebuchet">
		<!-- <xsl:attribute name="font-family">'Trebuchet MS','Segoe UI', Candara, 'Bitstream Vera Sans', 'DejaVu Sans', 'Bitstream Vera Sans', Verdana, 'Verdana Ref', sans-serif</xsl:attribute>-->
		<xsl:attribute name="font-family">'Trebuchet MS', sans-serif</xsl:attribute>		
	</xsl:template>

	<xsl:template name="tag_font_minion">
		<xsl:attribute name="font-family">'Minion Pro', serif</xsl:attribute>		
	</xsl:template>

	<xsl:template name="tag_color">
		<xsl:attribute name="color">rgb(242,101,50)</xsl:attribute>
	</xsl:template>

	<xsl:template match="/cv:CurriculumVitae">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>

				<fo:simple-page-master master-name="A4-new-cover"
					page-width="210mm" page-height="297mm" margin-top="22mm"
					margin-bottom="14mm" margin-left="12mm" margin-right="12mm">
					<fo:region-body margin-top="28mm" margin-left="18mm"
						margin-right="18mm" />
					<fo:region-before extent="28mm" precedence="true"
						region-name="new-cover-head" />
					<fo:region-after extent="9mm" precedence="true"
						region-name="new-cover-footer" />
				</fo:simple-page-master>

				<fo:simple-page-master master-name="A4-cover"
					page-width="210mm" page-height="297mm" margin-top="22mm"
					margin-bottom="14mm" margin-left="12mm" margin-right="12mm">
					<fo:region-body margin-top="28mm" margin-left="18mm"
						margin-right="18mm" />
					<fo:region-before extent="28mm" precedence="true"
						region-name="cover-head" />
					<fo:region-after extent="9mm" precedence="true"
						region-name="cover-footer" />
				</fo:simple-page-master>

				<fo:simple-page-master master-name="A4"
					page-width="210mm" page-height="297mm" margin-top="22mm"
					margin-bottom="14mm" margin-left="12mm" margin-right="12mm">
					<fo:region-body margin-left="18mm" margin-right="18mm"
						margin-bottom="12mm" />
					<fo:region-after extent="9mm" precedence="true"
						region-name="footer" />
				</fo:simple-page-master>

				<fo:page-sequence-master master-name="new-cover">
					<fo:single-page-master-reference
						master-reference="A4-new-cover" />
				</fo:page-sequence-master>

				<fo:page-sequence-master master-name="cover">
					<fo:single-page-master-reference
						master-reference="A4-cover" />
				</fo:page-sequence-master>

				<fo:page-sequence-master master-name="sequence">
					<fo:repeatable-page-master-reference
						master-reference="A4" />
				</fo:page-sequence-master>
			</fo:layout-master-set>

			<!-- THIS IS THE NEW PAGE -->
			<fo:page-sequence master-reference="new-cover"
				font-size="{$fontSize}">
				<fo:static-content flow-name="new-cover-head">
					<fo:block-container absolute-position="absolute"
						top="105mm" left="100mm">
						<fo:block>
							<xsl:apply-templates select="cv:CoverImage" />
						</fo:block>
					</fo:block-container>

					<fo:block-container absolute-position="absolute"
						top="210mm" left="20mm">
						
						<xsl:call-template name="frontpagename" />
						
						<fo:block font-size="10px" font-weight="bold" color="rgb(242,101,50)">
							<xsl:value-of select="cv:Profile/cv:Title" />
						</fo:block>

						<xsl:if test="cv:Profile/cv:PhoneNumber">
							<fo:block font-size="8px" font-weight="bold" color="rgb(242,101,50)">
								<xsl:value-of select="cv:Profile/cv:PhoneNumber" />
							</fo:block>
						</xsl:if>

						<fo:block font-size="8px" font-weight="bold" color="rgb(242,101,50)">
							www.sogeti.se
						</fo:block>
						<fo:block margin="0mm" padding="0mm">

							<!-- Base64 encoded file: sogeti logo warmred -->
							<fo:external-graphic content-height="scale-down-to-fit"
								content-width="scale-down-to-fit"
								src="url('data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAHcAAAAcCAYAAABbCiATAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAPnSURBVGhD7Zo9jxMxEIbzm2jpqfkBtIiOggYJiYISWgQNVCBaREFBgZCuPugQDRI9BQ136E53Fxa/c/uGyZy93nGcTZTY0irZXX/OMzMe2zvrWtpZCczmP79386+f2rVjMvh7/Kubnb193B3dv9H9eXJr6y706/fta+0qkAEMVuDiqpXgCWol9KvBLVPu6nABFjBqAW5wy8AKg9qWe/zopsDFb43U4G4BXEzeJy/vLblQ3OP5KqnB3SBcwDs/eC0BWWxuxHO8L4Xc4E4Id/7js8CCVdIFjw14kB/lUB71jEklcBH1M0jkL9o9unt9r4Kz7JyLwOjswzNZIo2F6MknIEL9qQDMC1cUJ/RZexLO/+sag2e8U+ZNwoWA7By67o6hPQvZAxflkaAstq8YqIULSz598zDkfy5jPX31oDv/+CKpyP/zXyq79hBUJvyiz9wU0h4EzyhT/Ici4r0oZLhHv1mWY8D9xeE7eY98HgWNwoXL3JQLQ7vaZXvgcq0OIVi4sF49JmknKDAEp/PSygHdPmd8cUVxQj1W6JxydF7CwzPUxf6w36zj5Okdgcmy+I/kAZtcCnnn0toWrZdRHrgAwiS7bgNzLBQAKTZWAEfSrp0CjtWJtmw9MbjIQ0Dau1i4Yv3K+1SD6xFmbahLWt7vmHn6Y60eAoYX4Paqrp/Re2wMFDatF8KWupQ15cau4aL80BRn4camlCqWuy17ubReD1wIhfOijcYBU1tXzLIoVAqbbcPaSuGiLBUspRCTwc1p5JTvJThaYW+Za2yC1JZXA649aNGGoetn4LRxuJsKpOzA0Q8vXAg35nm0FbOdGnBRF+ZGJBvELbnlMJahYGgyy0WkNqV1ptpCP7xwueywdbKuWAQaU4ZYsAW3zkMRXT9dtvUwuYAqFl+kFKBaQOUJGtapBByQxy3LGjEEULZfMcsgFLvk4RLJyiFlXYu1tZk+YnBTyjeZ5aJTJZsXKXde4uYpMK/lyvowwMVSBv8Xu1+RDQwogOT/diDj5fx58eW9uNpYv6EIqJ/55d5sLqAcPQX7T6iwfqusDLgkb2jXLqnkfX8+Hov6hwwsuUM1FjAGM7SmzL2/4kL7XSZqvsdy7aaDZ71uNzlyXmlbVhVFcBn6p+Zg0dB+uzC3c0LtG9q8564Moa4KNwdnH95nDw70MmLxEV1/ohM7w00GSOpsF65Nf5Bnger7UsvdB3i5MY6GawGgoMftoSPIz0BpCGiDW36Gq4G74WJyz7nhnEbpICEHuVluOWg3XFnvVfi+d+yXGQ1uBbixrxcYwm/yd1UvkfMiu/xeLFcOivtvl9vv5Tfcu3BhCv0HJlPqSmgJdswAAAAASUVORK5CYII=')" />
						</fo:block>
					</fo:block-container>

				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:if test="cv:Tags">
						<fo:block-container>
							<fo:block>
								<!-- Base64 encoded file: flippy.PNG -->
								<fo:external-graphic content-height="scale-down-to-fit"
									content-width="scale-down-to-fit"
									src="url('data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAEYAAAA2CAYAAAB6DO9FAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAANLSURBVGhD7Zotb1VBEIb7m7B4ND8AS3CIGhQCCbbB1RFsg0SRoEHiSPCImn6kpHA53HfTQzYn5+y8784sXDGbNG1z7+3OPvPs7Mfp0ZRtlcBRclknkGA2zEgwCUYrGmlMGpPGaATSGI1X1phDMebXp3fTzenxdPn0fvk+qv2+Op9+fnwz3Zw8mS4e35t23z5LXf0TY3bfv04/3j4vMBDk/IXfo9vuy4e/MOq+1CQMBQMgCKgOcPkzDIposOPy2YPNvtQkDAEDjS0gMyCo7mkwpAWkTgTgsS0cDAxYTpktYzCgXmMK/Lv60TKyToBSZ8LAKJZcvXhYCmNvK5Ys6tUWHJiLKa22EDDoGIO1MgdDPEAwOHze6gevw6YeIDNANxjoyWQPqxKs8jSmbiGW3ulZx+YCg4xYUPC61xIEDLCWKbDWC99tDAKwpg+gKAVvyyZm+qj7FMvcbmMYrSOUZqZqpCkuY7AqWFpD/Yh2/eqR2ZenyG7F2GUMs6GKmOvMFIpKwBKQDAbTw7Ilar5bNawcDjv2KIzJMhhmtxmxCqG2WAmAuaOaDMYKtmRxX4O87fbspQkG9WdUk8AwRRdgIuoLU3RH1RfAlsAwWey5FFrLOmOm92Tesm0MmICpxIA5mKnEGhOhOAMmatqumTPEmIjVggWDZI1oQ8BgUN6AWTA4j0UUe9cGj51K86A8yzYLBu/772cldrmunwL0HiSZ5bqGFw1HmkpQVsnk/N6eSyrm/mUZS9Tdj7yPwQeYI8EaPASNwbL3M8yRoHXJfvv+tescJRkDMMwh0rKqPIXEnazxdJA5RJp97c9TONSqBVoGAzjMtYMVMF4vN3yN0zFz7cD0g/eom8EuMGoRbgUP5VtNLcKtvpQrii4wGEhPcVwL2trvMBfurDXK9qEbDOBEZNMCg348hbiGZtW02lwXGPUx6Vpm2WCVR7+rq6J4qeUCMxNWd8Rz4Oq1ASD2rlTqRjMETNF9f9WgTC3PThWJsB701db0XLWGgZntKf+4c/cfU1tKM3XFOjFjGmNFaxmERCkFN6zGWMFDfQSGAUBltp5Yf3f5OiChn9LX3qbys/PpQbgx6qAO9f0JZiMzCSbBaJM2jUlj0hiNQBqj8foDyRhHTL9LpfIAAAAASUVORK5CYII=')" />
							</fo:block>
						</fo:block-container>
					</xsl:if>

					<!-- Tags -->
					<fo:block-container width="100mm">
						<xsl:call-template name="tags" />
					</fo:block-container>
				</fo:flow>
			</fo:page-sequence>

			<fo:page-sequence master-reference="cover"
				font-size="{$fontSize}">
				<fo:static-content flow-name="cover-head">
					<!-- Page border -->
					<fo:block-container border="{$border}" position="absolute"
						top="0" left="0mm" height="261mm">
						<fo:block></fo:block>
					</fo:block-container>

					<!-- logo -->
					<fo:block-container position="absolute" top="-0.8mm"
						left="0mm">
						<fo:block>
							<!-- Base64 encoded file: logo.png DO NOT REMOVE THIS!!! -->
							<fo:external-graphic width="55mm"
								content-height="scale-down-to-fit" content-width="scale-down-to-fit"
								src="url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAd0AAAEFCAYAAACmUD0sAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QYaCyce/g+H7QAAAB1pVFh0Q29tbWVudAAAAAAAQ3JlYXRlZCB3aXRoIEdJTVBkLmUHAAAgAElEQVR42u3dd5hU5d0+8Ps5zynTdpel9w5bWPrSi6CgiFgQwUYJCqiIClHsr5KgohJLNIotl0bTjCY/ezT6M4bktUQlKElU7BUDEQS2zJz2vH/MsstsY8ucZcv9uS6uxNkpZ77nzLnP9zlN7F04QoGIiIgCpXwfGstAREQUPGPckQxdIiKioPn79iBy4a0MXSIioqCFTlkBQDB0iYiIAiUUQosuBwCGLhERUVCU5yG0+JLyuGXoEhERBURr3w7mjIXl/62zJERERAF0uYk4ohfdD6UUhBAMXSIiokACVwFG4UTIQYWpnS9LQ0RElGZOKcLn/wRKpV5/ip0uERFROrtc30d40RqIaHaVv7HTJSIiSiPZoQPMOedU+zeGLhERUbq6XDuO8GWbqgwrH8DhZSIionQErlKwjj0VskdOjc9hp0tERJQGWjiE0A/W1focdrpERESN7XJLSxBd/4uUc3IZukREROkOXN+HdeJCyL7DDt0Ns1xEREQNp2VnIbTof+r0XHa6REREDe1y48WI3fbUIYeVGbpERESNCVzXRXjZFdA69qp7V8yyERER1Z9eMALmMUvr9RqGLhERUX2ZOiKX/7zGi2DUGNSsHBERUd2pRCliG54ENKNO+3EZukRERA0JXMdB+OzLIHvmNOj1HF4mIiKqS+AqBWPC9Hrvx2XoEhER1ZPs0AGRi+6s935chi4REVG90lIheuP/q/P5uDXhPl0iIqJaqHgxYrf8ASKUCYjGvRdDl4iIqKbATcQRXXsrZO+C9DTMLCkREVE1geu6CJ12DvSxc9L2ngxdIiKiyoHr+zAnHwXr5NVpfV+GLhER0cGBqwA9Jx/h829v1JHKDF0iIqJDkL37InrNbxp9pDJDl4iIqBYiKxvRdb8KJHABHr1MRESUDNzs9ojd+iwgrUACl50uERERAOgaYtc9GmjgstMlIiIyNGTc8SIQzQ40cNnpEhERA7eJApedbjVU8T40+jpfRNS0wlEIjT0E1TcBmzZwGbqVAzdegqw/fMxCELUw+y88GmrPLhaC6kxkt0fs5icBK9pkgcvQrTIXkoVXRbuhvv2I9SBq7j/ZzI4Qnfsnr2ZAVNflpl02Mm59DkqaTRq4DN0auB9sQenP1gCuA/C3XLdRAgBCSoBDfNSErGmzYS25iYWguq2nFKD3H4jItb+G0vQmD1yGbo2bQRogBCIXbYTWrgvrUbeiIfHHB+Bue4uloKajcRVGdQxc34cxYiwia+8P7MIXDN1GbhLJ3vkQnQewFnVd/8UeYxGIqPmtzh0H5rRjET5342ENXICnDLVI/s5P034RbiKiVhm48RKETz+vWQQuQ7clBu6Oj1B8/ZLDvuAQETX/wC1C9Mo7YZ54PgA0i/UmQ7elLDxKAVAo3rAYqngfEk/czoO8iIhqIgUy7nwB+sijm9VkMXRbCCEEEs/eDZTGITQJ+7mHgcQ+DjMTEVUOtvYdkLHpFYgu/ZvftHH2tAz+5+8g8ehd5ecSAwJFV8/lMDMRURnlupBDRiD205eAUAaa4+qRodsSAnf/dyi6/gcQoXDq49/vRslty1ggImLgxksRmn82opc/2CwOmGLottTA3fkJitZMh6hmVglNg/uvt1Fyy9LkQsehZiJqmy0uolfeAevk1cl1YzMeAWToNmOJP96HokuPh9CMGp8jNA3u++9g/5oj4H/6D4YvEbUpol0WYnf+qdkdMFUTXhyjOWykHTQUonZ/BfvVZ2A/+wCU40GYoUMvdEIAxUUo/vFiyEEFMGcuhDH2OACiWQ+zEBE1vLl1YRROQuTiTS3q0tsM3cO94Oz7D9wPt8D7YAvcLS/B3/ktYIUhhKj3rcqEacH//EOU3ns1Su+6FHrOMMhRs6APyIc2qLDaIeoWWTPPBZwE5MB8yG4DgIz2EJFsqB0fQjml8L75BP7nHwOGBRgGhOCADlGrWm/apQifdSnMY85qcY0FQ7cJu1j4Drz3X4f7xYfw338N7ofvQu3fBxgmhEzOChGKNPozhZSADMP77EO4n25HwnUAz4Xs0QcyZwy0wSOh98mF1iO/ZdXS9yB79oM5ZzmMwtkVj5d8DzhxiKyuKc/3v/o3nH+/icSjt5bXl4haOFNH7LrHIPsMTa7vWthIHtdEAYas2vkJnPfegvfuK/A+2QZ/107AtABZcXeLdIRsrQEsBGCYgGHC/24X/Fefg/rr04DnAr4L2S8XcvBo6AUTIPMnQRjhZhq4PkJnXgpz+hnl+6wTT/wU9vMPQ5WUJE+l8j3I3OGInH87kNUNWs98WD3zkfjVjQBDl6hlr1tdF/rIsYiuvT95U5oWimuiNPPe/184rz4Db+tf4O8r62LLholFJNYspjHZCUsAFvwdX8L75gvY//9xwE1A9s+FPnoWjImzoXXo3SyGbpRSCJ93PYzRs8s3JEofvALOqy8kh+EP2nDxPvsY+y+ZjcwHth78jev9efD9qjVL4/ep/P5owO4EojYTuPFihJddAfOYpcl1Ugv+LgzdtCwQ+2A/9yDsFx+BitsQRvJoY2GFWsT0CyEAXQd0Hf43XyLx9X1IPHYH5IBcmMeeBWPsnMMWvkopyF79ywP3wA/Q2fwMhGFW810ABQ3207fDPH51PbaiHcA0YAyfDK1XAbQOnSu6Y8+Bv3MH/I/fgvPua4A06xXCSinAdSCiURjDp0J0HQCtY9eK91c+VKIU6vs98L/cBu+z9+B/+xVEuOpGmuwzsMG19Pfuhr/nu+QPv+/Aem+MVLuR+flHyenq1f/Q91IWAt5nH3KFQfX67Wjt2yP2o+egZXerWF8xdNvmwiCEQOKJ25H4wz2AFUl2XYbR4r+bEAIIheF//TlK770KiUd/gvDyDZC5Ew5HoRFecX3qQ3Yc0Guvs/3K03UKXaUUtKx2CC9dB33I1ORju7+C/frzUF+9DwgN2oARME9YBiHOQxiA/bffI/6L9RDi0MGrfA/6gDyEFlwMbUBh8rHvv4Hz1svwPynrxmPZ0HoNgnncEghZsaG2b/moKiuY0MIroXXqDUSyUz8nUQThOakfXuk5ztO3ofSpR5J/uup3qc9144BdWu3rVMn3EFDJZT7aPuVv+88bC3gerNPWQhgWtL7Dqw/8L7YBmkTx+sUAT2mjOjUzpbBmz0do6fpWdRokQ7ehC8T+71Dy4wXw9n4PEYq22u8ppA61fz+Kb1oBc/pJCC1e36Rdr5ASWtecqu3sITYalJ2A2r+zbAUvagxcY+QkhFfeWbGf+NlNSDx6R/kR5ACg3ngR8YfWI3L5/ZB5k2BOngdz8lwUXT4Lavd3tQSuj9CZl8CcvrD8/eOP3gD72V8CppUynKx8H/GHrkPGptchzAiU8qud3qJ1CwHfQ8bGZyE69yv/W8ktZ8H74J+V2lAXeu5whK/6XfK7+F7Fe3kehJRQykfJ5bPg7fiybL+4j4yH/ply4Nn+VVOTxwAAENEIYj96HKJT37I3Sv5P8Q1nl32mjYwH/5X6+nMLoRKJsjWOwVPY6BArVwCGQOz6hyEHj211pz1yJ1IDeJ9sQdHFM+Dv29tmViDCMGFvfgbFN5zatN+5ms8S4VhFV1bjVnIJ9p8/DfsvmF7tML9SClqnLgivvLM8qL2P30biD/dChCIp31FoGkQogpJbVgG+XfZ6gdiNf4Kq3F0e9P7WSUthTl9Y/v6J5++B/dLjEKFwlf23QtMATUfJTYtq3KpPjqSYgBmC2vVZpfljAWYIwgpX/ItkwP38E6g939RYp8Qvr4a3+7/J72yFAavqgXQHvydchaLrzqx2+RCGCRhhoPIGg5Dlf2fgUq2/W9uGPv4IZD74FrRBYytG3loRhm59Fgil4O/fjeINS+s0tNjqglfT4H36IUo2LmnKoledDt1E7PrHoTy79oAKlQVFDd8luu7xlHlbcsu5te+r1TSU3n5OypZ39PL7q0yDUoDQdVhzLix/b6UUEr+7u9YViBAC/pefpXUlIwTgb/lj2fRX/W7O63+u/ywpKkqeK02Uzp+58BFduxGRVbdDKYHWun3G4eV6rcAESjacASH0Nl0D9/134Lz6OPQJ8wLfClWeB++TNyD7j0vNv55DkPnzbUi88CDcv/0e3qfbk52ebtThB64gMrMhzIN2CzhxqIRTa+gKIeB99F7Kd9YGFAKukzwtq+ITEDnvuvJwFkLA3fJsnaYNSF4wpcrRzY0Qf+G30N54Ef7e3eWP+R+/CaFJwHXrPw+FBv+jv0PoJnhTZ2r0bzwRhzHxSERW3QroVqvsbhm6DeRuewXef75N6+kjLTJ4pUT8Vz9BxsRTmiLlUXrPNYjd/EK1f7aOWQrrmKUAFNxtr8Dd+hc4rz6TDFC95sU7dPySlBCGEoc++hZI2S+azB8JfdhoeO9tS+mi5bDU68Amnn6gzqMp+y+Ynnwfq+HnTMucYdAyO8J582WovXvg7d2TsjIr3rC8bA1Q/32sQtNQfOM5DX49UfnGr6EhesUD0PMntfhTgepKU8X7OPfrKPHbm9p84B68deq+/WyTdNb+nl1IPHH7oZ4JWTANoUXrkLHpLYRX3QTZvRdUtR2jgj55QepnvPN8ncKjuvczxx5b6Vclq6xcvM8/rvP3Ld9/2gihU9citHxjzZ/TyH2s3EdLjVp/lJbAnDYbmQ9ugcyb1Oq725TVg4hmcgmoy0Ky/7/wvviUhahod5B4+sGm+SghkHjmF4j//FIoeLU+7wBj1CxE1j0Ba+5yKK9Sdyp1CGmlNrDf76z7snDQgUlKKcjx81K7Zl2vtJ9XBX4FHeX7gO9DuS6UE4fWM5/LKDW/9ajnQ+vUERk/fQrhZTeU7YJpWzXg8HId+d/tSF5An8oDTu39bxNmvAb79Zdg//UZmEfMhTH5BMhB48qDrrqtZKUUrONWQu3bBefPT6VsMFR57nff1H3FsetTiOzuFXXwvZRpkAPzUqfHVwh6zWIdfx5U0V6ISAx6wRFcQKn5Ba4TR3jJJTCPPbt8o7QtjpQwdOu6wOzdXXbpRCqvSaK0yYMeZhj2q3+EvfkpwDJhjpgKOXoG9MJjqhzgJkTy1obW/Cthv/j7igOZ0v5DF8lu9sAeqSqXeAy+Nt7HW6B2fJH8OCtUvkFCdPjXE3EYY6YivOpmiHBWmw1bhm59Fxzel7a6ohy2LhuGCfiAs2Uz7DdfBpxLYIybgdCyDYAZrbihhBDJg32y2gHFxbVMdz2+i6rugYYtG+bx5xzys9XOT+G88WLtofv+m3C3/wtCCNivvYDMB7dx+aTDu3rwPMiOHRC+4GbIQWNa1VWlGLpNsaKXksFbWTO5QL+QOiB1OFv/F+7q6cjY9GaV58jeA+G9907Z2qDqwVCia/+6f16P3NQH3ETKcqH2761fqCtAZGfDnLKgykae89oT8HbW71gCYZjwP98Krc8ILqN0mLbFHYSXXgpz5uI2PZTM0G3Mij0zO3kpPN1gMQ7UxGqC2wAKwBgzA1A+vK+2w9/xVa0dsHI9OC/dD2PG8tTQjWaUH4KlPBfKS6QcTKVFs+o+SbEOqV3mP55PmYYq05i8C0O17KfvLdsO8CH2/wfG7AvK38f9x7MofWBdg46YL77lfEDy2jfUxIFrl8KcOQ/hs9eXHzvBsK3Uq7AEdSxU516AnWAhDu7Esjo1QehqCC27CaHlGxE6dnGdXuK8/4+qj332QUqSu3/+Zcp30UbMqtPwV3W330v89clKxal0W0ChQbTLPsQbiyrLl//Juw0/RS0eB4pLkpPj2FAl+6EcmwsuBbM+iMchcwqQce+fEV52PdrGGbcM3WDX/eEsyD79WYiKpIJ1zKlNG/IFR9Vtv1DlyzJ6DvxvvkwNyhd+m9KdJlOuDleBOvgmBUpBKR/eB9sqfZ4H/9vUW9iFT1resDY/DcInLETmL7cjfMpy7lej9P4uHQeyZ2/ENj6K6NWPQGR1ZXfL0E0f67TLarjYQhvcCLEs6Aednxr45x24baLjHGo1AOuoBakd46dbU073Sp7utBsorrgsIkwLIho5ZPDrQ8ek7KPytv4JkFV3OZTcfkHK6+Tk0w/b9Yq1vInJ/+Oy06U0ha3nQcvORvTimxHd8CS0PkMZtgzd9NOHToN2iBVzm/jB+T7MWWc0edckrCiMwsm1d8OmBZk/PeXx4uuXVlkZKKWwf938g3pKgeiVD1e5kEblDjq88q6KW/55NkruuqzKGUhCCPjf7YT796dSrr9sHVt7zYIIZaUUtNwp7HApbWErIhbCK65C7I6XoY+ZzbBl6AYrcsXDUCVFTbOAH7KrS1251hoY6VyJt+8E6/gLm/yHppRCeNUm6AVjoeIlyasveV7ynx2HMbgAsdteKg8Yte8/KFozNXnbu2o6Z/X9HpTetarie3UdCGveuVAlRSkjGsnPcBD54R0V7w0fxVcfD6HpNXbmJfdeA2/rS+Xvb51yGcypc6DseMX7KJUMW8+BcfSy8seqW5GVz2PHrrif7cGB7TrJ/beODZWIQ5UWwzzieKAOR90rz4NyXSBRWvU8Zt+Bcpzkc2oI7+TrHah4cZXXKydxyNdT89/QhgTCZ12KjHtfhzntVBaloc0bS1DPrZSuAxA+fwNK770WwjSDC7aMLIRmnY7Sx+6pU7gJw0TozItRev86CDMUzHQBEJpAbMPTTXf6lFJwXn8Sek4hRHYPAED4wk0IFf0X3kf/gCotATQB2XcItC4Dki/ZvwuJFx5G4sn7kvferalmQsDZ+irciyYitPAqGGOOgzX7XJgzFsF57WmofXsBBWhdusMYd3z56xJ/egiJX98MYdZ+9LaQEiU/uwTmhFkw5yyD6DoIoUU/hnnk6XDffQ3+l/+G6DYYsntv6KNnJfPtm/fhfvQu/K+2w/vg7SrTGzrnesjufVNuYA8A4SU/AkorNgZFu85AdtfUC4bUcClKpRTCy38MEcuE7JOfvPvQQWL3vgl/+5vw9+5B/IH/qfY859BZ10Lr0AWy7xCISsPtGQ9sgb/97/D370X8vqsO2/nd1LCwFYaG0OkXwJq9jAVh6B4exoSTgUQJSn9+HUQ4mubuNgFrxnxYZ16LfUvyIKxIHV9nw333b4jd8gJKN54N77tdaQ1FpRSEZSF249NQWhNe6F4plN5/LeDaEFlZkH1yoPXIgZbdLXkaFwD4Cs6WV6B2PAB3+9vJg6ascK2Be3CQoTSB0nuuROmmy6Dnj4bMmQStUxeIjslQVfESxB+7Bf4Hr8N9/x0gHD1k4FYErw77jRdh/+0ZyJ59IfsMgeg6AFrHrpAdpgOeA+/rz+FsXg7nvbcA20lezKOGc6C9ra/A29qw0PJ2fFLz3975CyAE3Fefqa1YNZ765G/bDF+TcDf/oZYtVo2B21LCtmwYOXTyOQxbhm4zCd5pC6H1KUDJxhWA4zb60oLK9yEyMhG94AHI/iNRevcFdQ7c8q7t7b/APO5sRG98AYmn70Li93fVORxqD3QHxvDxCK++p+zm0k07rCykBGQYiNvwPtgG74NtyWHKgw9q07SKq1CFIg34DB2ADm/7P+Ft/2fqUL0Q5SEoIhn1f28hADMEf+e38Hd+C6VeSpn2A6cFCWiAWfv1vZ23Xm5cLauZd8ll55VGva+zZTNXCq0kbLWMKEJnrIZ5BIeQg8B9uo0g+45Axt1/hzFrEZQTb9CRzcr3oTwHoQWrkHHbXyD7j4S79UU4b9V/JSY0iZKbVgAArOPPR+xnm6HnjYIqLW5QZ6vsBET7joj88A6EV98HQGs2B0wIISCkrPiX5ulKee80X3mr8rQTHfawdRxoHTsgsnIdMu59jYHLTreZKlvPh+atQWjeGjibH4Xz2lNw//V28jQSqVdZYSvfA1wXUB7kgHyY006DMbXiKFp/99coue2iBl/tSdk2iq89EdEfPQkR7YDwmvsR2vM1Es89CPfN5+Hv/i55VS2pp166UKnkDdpdByIWgzlyGoyjToPsN4oHvxC11rCNl0LPyYd1+mroQ6ayIAzdlsWYeiqMqcktRO+jN+B9/RnUnl1Q//kUEIDongvRLht6r0HQ+gwDIFICzd+3C0WXHtuoyysKIeB9/QVKblmKyMVl97tt1x2hM68Gzrwaav9OeJ/9E96Or6D27AD27gKkAdGlH7QuPSD75kPr1K/KexJRKwlapYCSIhjTT0Do5OXQeuRxw5qh2/LJgeMgB46rsrBXDrAD/+3v+BBF15wCoTX+2s5C0+C+txUlG5cgsvYXqX/L6Ax96JGQBapKp8twJWrFYet5EBKw5iyGNfdcCCvGmxEcBtyn24SqO+8SAJy/PoqiK06qcj/YRgfv9m0oumwm1O4vDzkt/NERtdKwtW1o2VkIL7sCmY+8i9Bpl0BYMf7u2em2oR9BWVepinah9O5L4H7wTiDn1goh4O/ejaK1cxBasArmrBUsPlEbWceokv0wJxwJ68TlkIPHsigM3Tb8g/jPx4g/eTecvz4LEYlB6MHNBiEA6Abij29C4ol7Yc27AObMJQDvAkLU+tYtrgMtIwrrmIUwTzgbwohwfy1Dtw3/IBLFKL7sGPjf/RcwQ4AVbpJLN5Z/vuMh/pvbEH94A8wjT0Zo6QbOFKJW0NWitBj6qPGw5iyFPvzIShve3MBm6LZVrgNrwcU1Xo6vSRkm5wdRSw5bx4bWPhvW0WfAnHkqRLQDu1qGLqVscUbbwZg4l4UgooYFrecBbinMScfCnL0IctAYdrUMXSIiSlvQKgWUlkDPzYdx9Jkwj5hf8TgxdFsNTcJ++TEgms1a1K2Ph/flJywDUbrCNpGA1rkDrJlnwDxqPkSsI7tahm4r5XuAayPxx4dZC6LmzLVbV9A6NrSsTOjjZ8OcMReyVwHnMUO3DRRl+BRk3PEyC0HU3KXhLlqHP2gdCFPCmHwczKlzIHMnJh/n8DFDt9U7cEk0IwRkdWU9iFqKFjbUqlwXED7MCTNhzpgPmTepStBy+Jih2/p/t6EI9p48ALxwBFELE46m/RaM6e9obQhLhzFpDowx06CPnMmgZeiSiGayCESUnqC1E9Cy20EfNR3mEXMgcyYwaBm6RESUlpBVCkjEoXXtAmPiSTDHT4PWbySDlhi6RERpCVrPA+Il0AuGQx8zC8b4GVXuSc2gJYYuEVFDg9axAQ0wRoyHMXEOjIlzgDTcC5sYukREDFnfB+w4tG7dYRTOhDH+SMjB45N/U6rFHT1NDF0iombWzTqAb8MYPg762KNhjJsJkdm1ImjLcNiYGLpERPUNWc8D7AS0Ht1hjJ4JY/QUyCFTqn0ug5YYukRE9QlZpQDHBnQBY8RE6GNmwBg9HSKjc5VuloihS0RU36B1HSARh547BPrYWTBGToLWZxi7WWLoEhE1OmTLhoxlz56QQyfBKDwC+vBpACSLQwxdIqJGhazvA04CWrss6COnQh8xGXrBhEpDxuxgiaFLRNTAkLUhomHoQwqhjzgC+sgp0Dr0Lg/Zg4eJOWRMDF0iojqHrAJcG8LQoOePhj52JvQhY6F1G1zt8xmyxNAlImpgJyuHTIQxpBBab97cnRi6RESNC1nPAxwbWvt2kIOHJ4eLh4yB1nUQi0MMXSKiRoWs4wCuDa1rV+gFE6APGQs5ZCy09r1YHGLoEhE1OGB9H3BdwIlDzx8OmTcB+tBCyLyxEEY0+ZxKBz4RMXSJiOoSsp4LOA609tmQg4dD5o+HnjMUsv/oiufw6GJi6BIRNbyLlYNyIXPHQs8fBX3wKIjsHjW+jiFLDF0iokOF7IF9sR07QC8YD23giGQX229UjV0sETF0iehQAeu6gOtAZEah982BljcZek4+ZO5YCCPCLpaIoUtEDQpYzwNcB9AU9EFDoA0YDj1vFPScURDturNARAxdImpQwPo+4DiAm4AcnAfZfxj0/ELIwSOgde5f8TwOExMxdImongHrOoCTgOw3ALJ/AWT+WOiDhkPrmVdrwDJwiRi6RFSngB0I2X9IMmD75UDrO5IBS8TQJaLGBaxd1sEOgcwfB73fYAYsEUOXiBocsJ4HeG4yYPsPgOxXcFAHO4IBS8TQJaIGB6zrAK4DPScP2oGDnHr0Y8ASMXSJqMEBe/AQcf8BkDmFyYDtmwetR26tr2XAEjF0iaimgFUqeblE14bWuRPk4BHQy65JrPUZwQIRMXSJqMEBWzZMLLIyoPfLgcyfkjwnNncchLTKn8eOlYihS0T1CdkD+2EloA8eCpk3AXJADvSc0RCZXWoMWAYuEUOXiGoL2AN31XHLzoXNHQM9f3RyP2z3nBpfx4AlauOhq4r3QUQzWQmimgL24P2wXTon98PmjYfslwM5aAwLRER1D10GLlGlgPU8wLUh2mWV74fVB+VByxsHoZnlz2PXSkT1Dl2WgNp0yB7YD+u50IeOghwwHDJvZHI/bEbnGgOWgUtEDF2iQ3WxjgN4DmTPXpC5oyHzxkAfPBxat8E1vo4BS0QMXaJDBazvA04CIjsLer9cyLwp0AtGQOs/AkIzWCQiYugSNaqLdW3ouUMg88ZBDhgCvWACREan8uewayUihi5RfQO27GCn5NHEIyEHjYI+qAByYGGNr2PgEhFDl6iuXaxnQ88ZApk3HnreSMic0RCxjuxiiYihS9TgkPU8wLEhomHoBWMhCyYmu9gBo9nFEhFDl6hRXazrAp4N2bM3ZG4h9OETIQePgta+J7tYImLoEjUuZJOn7ZQPFQ8dC5k7BsKMsYslIoYuUaNC1nEA34E+dDT04UdBz8mHzJ3I4hARQ5eo8SFrA74LfWgh9BFHQS8YCdl/VPnf2bkSEUOXqKEhWzZcbAwrhBxee8gycImIoUtUn5D1vOQt7QYMhl44C8bQ0ZA5ExiyREQMXWp00Po+YMehdesGc9SR0IeNhz5qBgCtynMZskREDF2qbzfrOoDvwSicBH3M0TBGToFo153FISJi6FJautlEHLJvX+ijZ8AYNQUyZ3x5CLODJSQHCBoAAAf3SURBVCJi6FJjgrbsPrLG8DHQJ86BMXoaRGaXKs9j4BIRMXSpviFbdvUnYQgY44+GMfk46MOnAdDYzRIRMXQpLUHr2BCxCKxpc6GPmgp9yJSKv0GwmyUiYuhSuoLWmHgM5ICqt7xj0BIRMXSpoUHrOhCWDmvGghqDloiIGLrU0LD1PMB3YU4+BsaR86DnT2ZRiIgYupTWrtZJQC8YCXPm6TDGH8+iEBExdCm9QetAy8qANecsmNPnQmR0KjsYioiIGLqUprCNw5wyC+bRZ0AOHpvydx4MRUTE0KXGhq3jQMuMwZpzFqw5PwA0k10tERFDl9La1doJ6MNGwzppBfSCqexqiYgYupT2sPUcmFNmw5q/ElqnfuxqiYgYupTWsPV9QLkIzV8Jc/ZiCCuDXS0REUOX0hq2ngcR0hE+/RKYMxexIEREDF1Kf9i60DIzEV58GYyJJ7AgREQMXQqis9WyMhFefDmMCXO4v5aIiKFLaQ9b34ewdISX/BDmjEXlYcv9tUREDF1KV9gqBaE8hBashHXieeWPM2yJiBi6lLawBWDHYZ24GKEzLgGEwaIQERFDN+2BaydgjBqH8IrrILJ7sCBERMTQTXvY+j60dlmIrLoLet5EHiRFREQM3fSnLaB8G6EzVsOas5wHSREREUM3kLx1HOhDRyJy0W0QsY4MWyIiYuimPWyVgpAaIqtvhDGe59sSERFDN5jAtRMwJ89EeNUtgNDZ3RIREUM3qO42es190IdMZkGIiIihG0jgug6MYYUIr90EyBALQkREDN1AAtdzEFm1Hsakuclul0PJRETE0E1z2Po+tE6dEfufB6F16AmA+26JiCg9NJbgoMC1E7Cmz0bG7S9CtOdVpYiIiJ1uQInrIXLp7TBGH83uloiIGLqBZK1S0LIyEV3/G2jsbomIKEBtenhZuS6MYaOQcddmiOzuXBqIiIidbiCB69gIzV8Ga94aHp1MREQM3eA63ASiV90Nfeg0ANx/S0REDN1gmAYyNv4eokt/zn0iImLoBtLdKgWtY0fErv8dEMkGm1siImpqbeJAKuX70AfmIOO258sCl4lLREQM3fQHrufBKJyE6LpHoSAZuERExNANJHAdG9ax8xFZczcAHjBFRESHV6vdp6vsslOCTlnDuUxERAzd4DrcBCIrroBx1CLOYSIiYugGGrir1sOYPI9zl4iImpVWtU+XgUtEROx0mypwL1gPYxIDl4iI2OkycImIqG2Hrire1woC9zoGLhERNf/QFdHMlhu4dgKRC6+DMelkzkkiImr+odtyO1wb4VPPgTGRgUtERAzd4ALXdRGatxTmyRdxDhIREUM3sMD1PJhTZsKafwnnHhERMXQDC1zfh14wAuGVt3LOERERQzewwFWA7NED0SsfhlKKc46IiBi6gU1oZgZiNz8HpRTvFkRERAzdwEiB2G3PMnCJiKhFa/aXgVSeg4yNTwBmjIFLREQM3cAC10kgetkd0LoO5JwiIqIWr9kOLyvXQXjRaugjZ3AuERERQzewwPV9GOOnwTxuBecQERExdIMke/VG5KKfce4QERFDN0giZCG6/lGei0tERK1OszqQSnkOYusfA/Qwj1QmIiKGbmCBaycQWX0Dj1QmIqJWq1kMLyvfh3nUCTAmnMQ5QkREDN3gEheQvfsgvOJGzg0iImLoBsqQiF77Kx44RURErd5h3aer7Dii1zwCWLzEIxERMXSDC1zXRei0ldAHFXIuEBFRm3DYhpf13AJYcy/gHCAiIoZuoEwD0Wse4X5cIiJqU5p8eFnZccRu+DUUJPfjEhERQzewwPU8hOadBdkrj5UnIqI2p+mGlxUg+/aDtWAtq05ERAzdQAmf5+MSERFDN/AmN5FA5Ic/gTB5Pi4REbVdge/TVUrBnHYs9JEzWW0iImKnG+gHZGUhvPIWVpqIiNjpBtrlJkoRu/p3UEpxWJmIiBi6gQWu5yF0+kpo3QaxykRERAhweFn26A1r7oWsMBERUZChqzwb0Wse4ulBREREB0n78LJyXYSXXAyR0ZHVJSIiCqzTVcm7B5mzzmJliYiIAg1duIis3cRhZSIioiBDV7kOQsuugIi04+lBREREgYWuAvS8oTCnncGKEhERBdvpuohcwmFlIiKiQENXuQ7Cy67ksDIREVHQoasPLoAx7XRWkoiIKMjQVZ6NyNq7OKxMRERUl0a14YHrIbxwDUSsA6tIREQUZKcru/WAOXsZK0hERBRk6CongciV93FYmYiIqB7qPbysfB/WSUugdejF6hEREQXZ6WpZGQiddhkrR0REFGToqngpImvv5LAyERFRA9R5eFkpBfPIEyD7jWTViIiIgux0ha4hfO4NrBgREVGQna5yHETOvwEQOitGREQUZKer5w2FMf4EVouIiCjI0FV2HJGLbuPBU0RERI1U63ix8n2EFpwL0a4rK0VERBRkp6u1y4Q17yJWiYiIKMhOVyXiiF6YvDE975NLREQUVOgqwBh3BGTOeFaIiIgoTaofXhYewitv5MFTREREQYau8jyETr8QItyOw8pERERBhq7WoQPvk0tERBSAlH26Kl6K6MUP8eApIiKiQENXAcbkGZB9h7MqREREAdBU8b5k5vo2IufdzIOniIiIgup0RTQzeeWp01YBZgQcVCYiIgqo0wUArXM3mLOXshpERERBdrqqpAixNY8A0mI1iIiIAiTszY8rY8o8VoKIiChg/wddWJ2i+ad3eAAAAABJRU5ErkJggg==')" />
						</fo:block>
					</fo:block-container>
				</fo:static-content>

				<fo:static-content flow-name="cover-footer">
					<xsl:call-template name="footer" />
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">

					<fo:table table-layout="fixed" width="100%" border-bottom="{$border}">
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell width="110mm">
									<fo:block font-size="14px" font-weight="bold">
										<xsl:call-template name="name" />
									</fo:block>
									<xsl:apply-templates select="cv:Profile/cv:Title" />
									<xsl:apply-templates select="cv:Description" />
								</fo:table-cell>
								<fo:table-cell text-align="right" margin="0mm"
									padding="0mm" height="50mm">
									<!-- <xsl:apply-templates select="cv:Profile/cv:PortraitUrl" /> -->
									<fo:block height="100%" font-size="0pt">
										<fo:external-graphic border="{$border}"
											content-height="50mm">
											<xsl:attribute name="src">
							                     	<xsl:value-of select="cv:Profile/cv:PortraitUrl" />
							                     </xsl:attribute>
										</fo:external-graphic>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>

					<fo:block font-size="{$headlineFontSize}" font-weight="bold"
						margin-top="8mm">
						<xsl:choose>
							<xsl:when test="//@language = 'en'">
								SUMMARY
							</xsl:when>
							<xsl:otherwise>
								SAMMANFATTNING
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>

					<xsl:call-template name="personalQualities" />
					<xsl:call-template name="importantTechnologies" />
					<xsl:call-template name="importantEngagements" />
					<xsl:call-template name="importantCertifications" />
					<!-- 
					<xsl:call-template name="importantEducations" />
					 -->
					<xsl:call-template name="importantEmployments" />
				</fo:flow>
			</fo:page-sequence>

			<fo:page-sequence master-reference="sequence"
				font-size="{$fontSize}">
				<fo:static-content flow-name="footer">
					<xsl:call-template name="footer" />
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates select="cv:Engagements" />
					<fo:block id="last-page" />
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template name="footer">
		<fo:block-container width="100%" height="9mm"
			background-color="{$borderColor}" color="white" font-size="8px">
			<fo:table table-layout="fixed" width="100%">
				<fo:table-body>
					<fo:table-row height="9mm">
						<fo:table-cell text-align="center" display-align="center">
							<fo:block>
								<xsl:value-of select="cv:Profile/cv:Name" />
							</fo:block>
						</fo:table-cell>

						<fo:table-cell text-align="center" display-align="center">
							<fo:block>www.sogeti.se</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" display-align="center">
							<fo:block>
								<xsl:value-of select="cv:Profile/cv:Title" />
							</fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center" display-align="center">
							<fo:block>
								<fo:page-number />
								&#160;(
								<fo:page-number-citation ref-id="last-page" />
								)
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block-container>
	</xsl:template>

	<xsl:template name="tags">
		<fo:block>
			&#x200B;
			<xsl:for-each select="cv:Tags/cv:Tag">
				<fo:inline padding-right="3px">
					<xsl:call-template name="tag_color" />
					<xsl:if test="@bold='true'">
						<xsl:call-template name="tag_bold" />						
					</xsl:if>
					<xsl:if test="@italic='true'">
						<xsl:call-template name="tag_italic" />
					</xsl:if>
					<xsl:if test="@upperCase='true'">
						<xsl:call-template name="tag_uppercase" />
					</xsl:if>
					<xsl:if test="cv:Size = 'LARGE'">
						<xsl:call-template name="tag_size_large" />
					</xsl:if>
					<xsl:if test="cv:Size = 'MEDIUM'">
						<xsl:call-template name="tag_size_medium" />
					</xsl:if>
					<xsl:if test="cv:Size = 'SMALL'">
						<xsl:call-template name="tag_size_small" />
					</xsl:if>
					<xsl:if test="cv:Font = 'myriad'">
						 <xsl:call-template name="tag_font_myriad" />
					</xsl:if>
					<xsl:if test="cv:Font = 'trebuchet'">
						 <xsl:call-template name="tag_font_trebuchet" />
					</xsl:if>
					<xsl:if test="cv:Font = 'minion'">
						 <xsl:call-template name="tag_font_minion" />
					</xsl:if>

				 	<xsl:value-of select="cv:TagName" />
				</fo:inline>
				&#x200B;
			</xsl:for-each>
		</fo:block>

	</xsl:template>

	<xsl:template name="frontpagename">
			<xsl:choose>
				<xsl:when test="string-length(cv:Profile//cv:Name) &gt; 12 and string-length(cv:Profile//cv:Name) &lt;= 20">
					<fo:block font-size="36px" font-weight="bold" color="rgb(242,101,50)">
						<xsl:value-of select="cv:Profile/cv:FirstName" />
					</fo:block>
					<fo:block font-size="36px" font-weight="bold" color="rgb(242,101,50)">
						<xsl:value-of select="cv:Profile/cv:LastName" />
					</fo:block>
				</xsl:when>
				<xsl:when test="string-length(cv:Profile//cv:Name) > 20">
					<fo:block font-size="24px" font-weight="bold" color="rgb(242,101,50)">
						<xsl:value-of select="cv:Profile/cv:FirstName" />
					</fo:block>
					<fo:block font-size="24px" font-weight="bold" color="rgb(242,101,50)">
						<xsl:value-of select="cv:Profile/cv:LastName" />
					</fo:block>
				</xsl:when>
				<xsl:otherwise>
					<fo:block font-size="36px" font-weight="bold" color="rgb(242,101,50)">			
						<xsl:value-of select="cv:Profile/cv:Name" />
					</fo:block>
				</xsl:otherwise>
			</xsl:choose>		
	</xsl:template>

	<xsl:template name="name">
		<fo:block font-size="14px" font-weight="bold">
			<xsl:value-of select="cv:Profile/cv:Name" />
		</fo:block>		
	</xsl:template>

	<xsl:template match="cv:Title">
		<fo:block>
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="cv:Description">
		<fo:block font-weight="bold" margin-top="5mm">
			<xsl:choose>
				<xsl:when test="//@language = 'en'">
					Description
				</xsl:when>
				<xsl:otherwise>
					Beskrivning
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
		<fo:block>
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="cv:PortraitUrl">
		<fo:block height="100%" border="thin solid black">

			<fo:external-graphic border="thin solid red"
				content-height="50mm">
				<xsl:attribute name="src">
                     	<xsl:value-of select="." />
                     </xsl:attribute>
			</fo:external-graphic>

		</fo:block>
	</xsl:template>

	<xsl:template match="cv:CoverImage">
		<fo:block>
			<xsl:element name="fo:external-graphic">
				<xsl:attribute name="src"><xsl:value-of select="cv:Url" /></xsl:attribute>
				<xsl:attribute name="width">90mm</xsl:attribute>
				<xsl:attribute name="content-height">scale-down-to-fit</xsl:attribute>
				<xsl:attribute name="content-width">scale-down-to-fit</xsl:attribute>
			</xsl:element>
		</fo:block>
	</xsl:template>

	<xsl:template name="importantEngagements">
		<xsl:if test="cv:Engagements//cv:Job[@important='true']">
			<fo:block font-weight="bold" margin-top="3mm">
				<xsl:choose>
					<xsl:when test="//@language = 'en'">
						SELECTION OF ASSIGNMENTS
					</xsl:when>
					<xsl:otherwise>
						VIKTIGA UPPDRAG
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:list-block>
				<xsl:for-each select="cv:Engagements/cv:Job[@important='true']">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block font-weight="bold">
								<xsl:value-of select="substring(cv:Date,0,5)" />
								&#160;
								<xsl:value-of select="cv:Name" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
					<fo:list-item>
						<fo:list-item-label>
							<fo:block></fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<xsl:value-of select="cv:Description" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template name="personalQualities">
		<xsl:if test="cv:PersonalQualities">
			<fo:block font-weight="bold" margin-top="3mm">
				<xsl:choose>
					<xsl:when test="//@language = 'en'">
						PERSONAL SKILLS
					</xsl:when>
					<xsl:otherwise>
						PERSONLIGA EGENSKAPER
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:list-block>
				<xsl:for-each select="cv:PersonalQualities/cv:PersonalQuality">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<xsl:value-of select="." />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template name="importantTechnologies">
		<xsl:if test="cv:Technology/cv:Skill[@important='true']">
			<fo:block font-weight="bold" margin-top="3mm">
				<xsl:choose>
					<xsl:when test="//@language = 'en'">
						TECHNICAL SKILLS
					</xsl:when>
					<xsl:otherwise>
						TEKNISKA FÄRDIGHETER
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:list-block>
				<xsl:for-each
					select="cv:Technology/cv:Skill[@important='true'][position() &lt; 11]">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<xsl:value-of select="cv:Name" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template name="importantProfessionalKnowledge">
		<xsl:if test="cv:ProfessionalKnowledge/cv:Skill">
			<fo:block font-weight="bold" margin-top="3mm">
				PROFESSIONAL
				KNOWLEDGE
			</fo:block>
			<fo:list-block>
				<xsl:for-each select="cv:ProfessionalKnowledge/cv:Skill">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<xsl:value-of select="cv:Name" />
								&#160;
								<fo:inline font-size="6px" color="{$borderColor}">&#8226;</fo:inline>
								&#160;
								<xsl:value-of select="cv:Level" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template name="importantEducations">
		<xsl:if test="cv:Educations/cv:Acquisition">
			<fo:block font-weight="bold" margin-top="3mm">
				<xsl:choose>
					<xsl:when test="//@language = 'en'">
						EDUCATIONS
					</xsl:when>
					<xsl:otherwise>
						UTBILDNINGAR
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:list-block>
				<xsl:for-each select="cv:Educations/cv:Acquisition">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<fo:inline font-weight="bold">
									<xsl:value-of select="cv:Date" />
								</fo:inline>
								&#160;
								<xsl:value-of select="cv:Name" />
								<xsl:if test="string-length(cv:Location) &gt; 0">
									,&#160;
									<xsl:value-of select="cv:Location" />
								</xsl:if>
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template name="importantCertifications">
		<xsl:if test="cv:Certifications/cv:Skill">
			<fo:block font-weight="bold" margin-top="3mm">
				<xsl:choose>
					<xsl:when test="//@language = 'en'">
						CERTIFICATIONS
					</xsl:when>
					<xsl:otherwise>
						CERTIFIERINGAR
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:list-block>
				<xsl:for-each select="cv:Certifications/cv:Skill">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<xsl:value-of select="cv:Name" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template name="importantEmployments">
		<xsl:if test="cv:Employments/cv:Job">
			<fo:block font-weight="bold" margin-top="3mm">
				<xsl:choose>
					<xsl:when test="//@language = 'en'">
						EMPLOYMENTS
					</xsl:when>
					<xsl:otherwise>
						ANSTÄLLNINGAR
					</xsl:otherwise>
				</xsl:choose>
			</fo:block>
			<fo:list-block>
				<xsl:for-each select="cv:Employments/cv:Job">
					<fo:list-item>
						<fo:list-item-label start-indent="1mm">
							<fo:block>&#8226;</fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block font-weight="bold">
								<xsl:value-of select="cv:Date" />
								&#160;
								<xsl:value-of select="cv:Name" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
					<fo:list-item>
						<fo:list-item-label>
							<fo:block></fo:block>
						</fo:list-item-label>
						<fo:list-item-body start-indent="4mm">
							<fo:block>
								<xsl:value-of select="cv:Description" />
							</fo:block>
						</fo:list-item-body>
					</fo:list-item>
				</xsl:for-each>
			</fo:list-block>
		</xsl:if>
	</xsl:template>

	<xsl:template match="cv:Engagements">
		<fo:block font-size="{$headlineFontSize}" font-weight="bold"
			margin-bottom="3mm" margin-left="30mm">
			<xsl:choose>
				<xsl:when test="//@language = 'en'">
					ENGAGEMENTS
				</xsl:when>
				<xsl:otherwise>
					UPPDRAG
				</xsl:otherwise>
			</xsl:choose>
		</fo:block>
		<fo:list-block>
			<xsl:apply-templates select="cv:Job" />
		</fo:list-block>
	</xsl:template>

	<xsl:template match="cv:Job">
		<fo:list-item border-bottom="{$border}">
			<fo:list-item-label>
				<fo:block>
					<xsl:value-of select="cv:Date" />
				</fo:block>
			</fo:list-item-label>
			<fo:list-item-body start-indent="30mm">
				<fo:block font-weight="bold">
					<xsl:value-of select="cv:Name" />
				</fo:block>
			</fo:list-item-body>
		</fo:list-item>
		<fo:list-item>
			<fo:list-item-label>
				<fo:block></fo:block>
			</fo:list-item-label>
			<fo:list-item-body start-indent="30mm">
				<fo:block>
					<fo:inline font-weight="bold">
						<xsl:choose>
							<xsl:when test="//@language = 'en'">
								Description:
							</xsl:when>
							<xsl:otherwise>
								Beskrivning:
							</xsl:otherwise>
						</xsl:choose>
					</fo:inline>
					<xsl:value-of select="cv:Description" />
				</fo:block>
				<fo:block margin-bottom="10mm">
					<fo:inline font-weight="bold">
						<xsl:choose>
							<xsl:when test="//@language = 'en'">
								Assignment length:
							</xsl:when>
							<xsl:otherwise>
								Uppdragets längd:
							</xsl:otherwise>
						</xsl:choose>
					</fo:inline>
					<xsl:value-of select="cv:Duration" />
				</fo:block>
			</fo:list-item-body>
		</fo:list-item>
	</xsl:template>

</xsl:stylesheet>