package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.OutputStream;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;

@ApplicationScoped
public class FopWrapper {

	private FopFactory fopFactory;
	private TransformerFactory transformerFactory;

	public FopWrapper() {
		// Keep this to satisfy CDI
	}

	@Inject
	public FopWrapper(FopFactory fopFactory,
			TransformerFactory transformerFactory) {
		this.fopFactory = fopFactory;
		this.transformerFactory = transformerFactory;
	}

	public synchronized void transform(Source xmlData, Source xslStylesheet,
			String mimeType, OutputStream out) throws FOPException,
			TransformerException {
		transform(xmlData, xslStylesheet, mimeType, out, null);
	}

	public synchronized void transform(Source xmlData, Source xslStylesheet,
			String mimeType, OutputStream out, Map<String, String> xslParameters)
			throws TransformerException, FOPException {

		FopErrorListener errorListener = new FopErrorListener();
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		foUserAgent.getEventBroadcaster().addEventListener(errorListener);
		Fop fop = fopFactory.newFop(mimeType, foUserAgent, out);

		Result res = new SAXResult(fop.getDefaultHandler());

		Transformer transformer = transformerFactory
				.newTransformer(xslStylesheet);

		if (xslParameters != null) {
			for (String key : xslParameters.keySet()) {
				transformer.setParameter(key, xslParameters.get(key));
			}
		}

		transformer.transform(xmlData, res);

		if (errorListener.containsFatalEvents()) {
			throw new FOPException(
					"One or more fatal events occured during transformation. \n"
							+ errorListener.getFatalEventInfo());
		}
	}
}
