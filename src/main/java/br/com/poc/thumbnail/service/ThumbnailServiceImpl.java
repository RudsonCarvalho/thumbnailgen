/**
 * 
 */
package br.com.poc.thumbnail.service;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.poc.thumbnail.exception.ServiceException;

/**
 * @author Rudson Kiyoshi S. Carvalho
 * @data 2017-07-05
 */
public class ThumbnailServiceImpl implements ThumbnailService {

	private final int THUMB_WIDTH = 250;

	public File genThumbnail(File file) throws ServiceException {

		BufferedImage bufferedImage = null;

		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException ioe) {
			throw new ServiceException("IOException trying to read image.");
		}

		int width, height;
		
		//nova altura e largura conforme proporcao da imagem
		if (bufferedImage.getWidth() > bufferedImage.getHeight()) {
			height = (int) (1.1 * THUMB_WIDTH);
			width = (int) ((height * 1.0) / bufferedImage.getHeight() * bufferedImage.getWidth());
		} else {
			width = (int) (1.1 * THUMB_WIDTH);
			height = (int) ((width * 1.0) / bufferedImage.getWidth() * bufferedImage.getHeight());
		}

		//reduz a imagem
		//BufferedImage resizedImage = new BufferedImage(width, height, bufferedImage.getType());
		//BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
		
		//redesenhar a imagem
		Graphics2D graphics = resizedImage.createGraphics();

		graphics.setComposite(AlphaComposite.Src); //alpha 1.0 composite rule
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

		graphics.drawImage(bufferedImage, 0, 0, width, height, null);
		graphics.dispose();

		// proporcao para gerar o recorte do centro da imagem
		int x = (resizedImage.getWidth() - THUMB_WIDTH) / 2;
		int y = (resizedImage.getHeight() - THUMB_WIDTH) / 2;

		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
		}
		
		//corta a imagem no centro
		BufferedImage thumbnailBufferedImage = resizedImage.getSubimage(x, y, THUMB_WIDTH, THUMB_WIDTH);

		// salvar a imagem		
		File thumbFile = new File(file.getPath().toLowerCase().replace(".jpg", "thumb.jpg"));
		
		try {			
			ImageIO.write(thumbnailBufferedImage, "JPG", thumbFile);
		} catch (IOException ioe) {
			throw new ServiceException("Error writing image to file");
		}

		return thumbFile;
	}

	public File genThumbnail(String filePath) throws ServiceException {
		return genThumbnail(new File(filePath));
	}

}
