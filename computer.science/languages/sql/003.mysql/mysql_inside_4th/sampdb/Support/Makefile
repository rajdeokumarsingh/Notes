STYLESHEET = changes-html.xsl

all:: ../Changes.html ../Changes.txt

../Changes.html:: Changes.xml $(STYLESHEET)
	xsltproc -o ../Changes.html $(STYLESHEET) Changes.xml

../Changes.txt:: ../Changes.html
	lynx -dump ../Changes.html > ../Changes.txt

lastchangeset:
	lastchangeset.rb Changes.xml
