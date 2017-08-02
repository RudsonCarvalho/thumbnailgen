/**
 * 
 */
package br.com.poc.thumbnail.service;

import java.io.File;

import br.com.poc.thumbnail.exception.ServiceException;

/**
 * @author Rudson Kiyoshi S. Carvalho - IBM
 * @data 2017-07-05
 */
public interface ThumbnailService {

	File genThumbnail(File file) throws ServiceException;
	
	File genThumbnail(String filePath) throws ServiceException;
	
}
