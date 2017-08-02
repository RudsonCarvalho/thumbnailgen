package br.com.poc.thumbnail;

import br.com.poc.thumbnail.exception.ServiceException;
import br.com.poc.thumbnail.service.ThumbnailService;
import br.com.poc.thumbnail.service.ThumbnailServiceImpl;

/**
 * Gerador de thumbnail poc
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ThumbnailService service = new ThumbnailServiceImpl();
        
        try {
			
        	service.genThumbnail("../imagem.jpg");
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
