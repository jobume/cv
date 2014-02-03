package se.sogeti.umea.configuration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.persistence.file.LocalImageFileBinaryRepository;
import se.sogeti.umea.cvconverter.application.Repository;

/**
 * Serves image from absolute path outside of web container.
 * 
 * Modified from:
 * 
 * @link http://balusc.blogspot.com/2007/04/imageservlet.html
 */
public class ImageServlet extends HttpServlet {

	private static final Logger LOG = LoggerFactory
			.getLogger(ImageServlet.class);

	private static final long serialVersionUID = -6711943931214544795L;

	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	@Inject @Repository
	private LocalImageFileBinaryRepository fileRepository;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Image servlet is handling request ... ");
		String requestedImage = request.getPathInfo();
		LOG.debug("Requested image is: " + requestedImage);

		if (requestedImage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		LOG.debug("ImagePath is: " + fileRepository.getFileLocationOnDisk());
		File image = new File(fileRepository.getFileLocationOnDisk(),
				URLDecoder.decode(requestedImage, "UTF-8"));

		if (!image.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			LOG.debug("Did not find image : " + image);
			return;
		}

		String contentType = getServletContext().getMimeType(image.getName().toLowerCase());

		if (contentType == null || !contentType.startsWith("image")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			LOG.debug("ContentType " + contentType + " was not an image.");
			return;
		}

		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(image.length()));
		response.setHeader("Content-Disposition",
				"inline; filename=\"" + image.getName() + "\"");

		try (BufferedInputStream input = new BufferedInputStream(
				new FileInputStream(image), DEFAULT_BUFFER_SIZE);
				BufferedOutputStream output = new BufferedOutputStream(
						response.getOutputStream(), DEFAULT_BUFFER_SIZE);) {

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		}
	}
}