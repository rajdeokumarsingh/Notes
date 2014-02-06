/* ---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
*/

package com.pekall.http;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP工具
 * 
 * @author
 * @since 1.0
 */
abstract class GZipUtils {

	private static final int BUFFER = 1024;
	private static final String EXT = ".gz";

	/**
	 * 数据压缩
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static byte[] compress(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 压缩
		compress(bais, baos);

		byte[] output = baos.toByteArray();

		try {
			baos.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
            try {
                baos.close();
            } catch (IOException e) {
                // Ignore
            }

            try {
                bais.close();
            } catch (IOException e) {
                // Ignore
            }

        }

		return output;
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	private static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

		compress(fis, fos);

		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
		}
	}

	/**
	 * 数据压缩
	 * 
	 * @param is
	 * @param os
	 */
	private static void compress(InputStream is, OutputStream os) {

		GZIPOutputStream gos = null;
		try {
			gos = new GZIPOutputStream(os);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = is.read(data, 0, BUFFER)) != -1) {
				gos.write(data, 0, count);
			}

			gos.finish();

			gos.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
                if (gos != null) {
                    gos.close();
                }
            } catch (IOException e) {
				// Igore
			}
		}

	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * 文件压缩
	 * 
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	@SuppressWarnings("SameParameterValue")
    private static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 数据解压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 解压缩

		decompress(bais, baos);

		data = baos.toByteArray();

		try {
			baos.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
            try {
                baos.close();
            } catch (IOException e) {
                // Ignore
            }

            try {
                bais.close();
            } catch (IOException e) {
                // Ignore
            }

        }

		return data;
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static void decompress(File file) throws Exception {
		decompress(file, true);
	}

	/**
	 * 文件解压缩
	 * 
	 * @param file
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	private static void decompress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT,
				""));
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
		}
	}

	/**
	 * 数据解压缩
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	private static void decompress(InputStream is, OutputStream os) {

		GZIPInputStream gis = null;
		try {
			gis = new GZIPInputStream(is);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = gis.read(data, 0, BUFFER)) != -1) {
				os.write(data, 0, count);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (gis != null) {
				try {
					gis.close();
				} catch (IOException e) {
					// Ignore
				}
			}
		}
	}

	/**
	 * 文件解压缩
	 * 
	 * @param path
	 * @throws Exception
	 */
	@SuppressWarnings("UnusedDeclaration")
    public static void decompress(String path) throws Exception {
		decompress(path, true);
	}

	/**
	 * 文件解压缩
	 * 
	 * @param path
	 * @param delete
	 *            是否删除原始文件
	 * @throws Exception
	 */
	@SuppressWarnings("SameParameterValue")
    private static void decompress(String path, boolean delete) throws Exception {
		File file = new File(path);
		decompress(file, delete);
	}

	/**
	 * 字符串压缩
	 * 
	 * @param result
	 * @return
	 */
	// public static String gzipStr(String result) {
	// String compressedStr = new String(Base64.encodeBase64(
	// GZipUtils.compress(result.toString().trim().getBytes()), false));
	// return compressedStr;
	// }

	/**
	 * 解压gzip压缩字符串
	 * 
	 * @param gzipString
	 * @return
	 */
	// public static String decompressGzipStr(String gzipString) {
	// String base64Str = new String(GZipUtils.decompress(Base64
	// .decodeBase64(gzipString.trim().getBytes())));
	// return base64Str;
	// }
}
