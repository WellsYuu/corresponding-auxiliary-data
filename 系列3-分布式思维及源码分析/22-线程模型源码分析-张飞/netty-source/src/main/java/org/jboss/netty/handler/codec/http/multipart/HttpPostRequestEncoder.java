/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.netty.handler.codec.http.multipart;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpConstants;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.stream.ChunkedInput;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * This encoder will help to encode Request for a FORM as POST.
 */
public class HttpPostRequestEncoder implements ChunkedInput {

    /**
     * Different modes to use to encode form data.
     */
    public enum EncoderMode {
        /**
         * Legacy mode which should work for most. It is known to not work with OAUTH. For OAUTH use
         * {@link EncoderMode#RFC3986}. The W3C form recommentations this for submitting post form data.
         */
        RFC1738,

        /**
         * Mode which is more new and is used for OAUTH
         */
        RFC3986,
        /**
         * The HTML5 spec disallows mixed mode in multipart/form-data
         * requests. More concretely this means that more files submitted
         * under the same name will not be encoded using mixed mode, but
         * will be treated as distinct fields.
         *
         * Reference:
         *   http://www.w3.org/TR/html5/forms.html#multipart-form-data
         */
        HTML5
    }

    private static final Map<Pattern, String> percentEncodings = new HashMap<Pattern, String>();

    static {
        percentEncodings.put(Pattern.compile("\\*"), "%2A");
        percentEncodings.put(Pattern.compile("\\+"), "%20");
        percentEncodings.put(Pattern.compile("%7E"), "~");
    }

    /**
     * Factory used to create InterfaceHttpData
     */
    private final HttpDataFactory factory;

    /**
     * Request to encode
     */
    private final HttpRequest request;

    /**
     * Default charset to use
     */
    private final Charset charset;

    /**
     * Chunked false by default
     */
    private boolean isChunked;

    /**
     * InterfaceHttpData for Body (without encoding)
     */
    private final List<InterfaceHttpData> bodyListDatas;
    /**
     * The final Multipart List of InterfaceHttpData including encoding
     */
    final List<InterfaceHttpData> multipartHttpDatas;

    /**
     * Does this request is a Multipart request
     */
    private final boolean isMultipart;

    /**
     * If multipart, this is the boundary for the flobal multipart
     */
    String multipartDataBoundary;

    /**
     * If multipart, there could be internal multiparts (mixed) to the global multipart.
     * Only one level is allowed.
     */
    String multipartMixedBoundary;
    /**
     * To check if the header has been finalized
     */
    private boolean headerFinalized;

    private final EncoderMode encoderMode;

    /**
    *
    * @param request the request to encode
    * @param multipart True if the FORM is a ENCTYPE="multipart/form-data"
    * @throws NullPointerException for request
    * @throws ErrorDataEncoderException if the request is not a POST
    */
    public HttpPostRequestEncoder(HttpRequest request, boolean multipart)
            throws ErrorDataEncoderException {
        this(new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE),
                request, multipart, HttpConstants.DEFAULT_CHARSET);
    }

    /**
     *
     * @param factory the factory used to create InterfaceHttpData
     * @param request the request to encode
     * @param multipart True if the FORM is a ENCTYPE="multipart/form-data"
     * @throws NullPointerException for request and factory
     * @throws ErrorDataEncoderException if the request is not a POST
     */
    public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart)
            throws ErrorDataEncoderException {
        this(factory, request, multipart, HttpConstants.DEFAULT_CHARSET);
    }

    /**
     *
     * @param factory the factory used to create InterfaceHttpData
     * @param request the request to encode
     * @param multipart True if the FORM is a ENCTYPE="multipart/form-data"
     * @param charset the charset to use as default
     * @throws NullPointerException for request or charset or factory
     * @throws ErrorDataEncoderException if the request is not a POST
     */
    public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request,
            boolean multipart, Charset charset) throws ErrorDataEncoderException {
        this(factory, request, multipart, charset, EncoderMode.RFC1738);
    }

    /**
     *
     * @param factory the factory used to create InterfaceHttpData
     * @param request the request to encode
     * @param multipart True if the FORM is a ENCTYPE="multipart/form-data"
     * @param charset the charset to use as default
    +  @param encoderMode the mode for the encoder to use. See {@link EncoderMode} for the details.
     * @throws NullPointerException for request or charset or factory
     * @throws ErrorDataEncoderException if the request is not a POST
     */
    public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart,
                                   Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
        if (factory == null) {
            throw new NullPointerException("factory");
        }
        if (request == null) {
            throw new NullPointerException("request");
        }
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        HttpMethod method = request.getMethod();
        if (!(method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH))) {
            throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");
        }
        this.request = request;
        this.charset = charset;
        this.factory = factory;
        this.encoderMode = encoderMode;
        // Fill default values
        bodyListDatas = new ArrayList<InterfaceHttpData>();
        // default mode
        isLastChunk = false;
        isLastChunkSent = false;
        isMultipart = multipart;
        multipartHttpDatas = new ArrayList<InterfaceHttpData>();
        if (isMultipart) {
            initDataMultipart();
        }
    }
    /**
     * Clean all HttpDatas (on Disk) for the current request.
 */
    public void cleanFiles() {
        factory.cleanRequestHttpDatas(request);
    }

    /**
     * Does the last non empty chunk already encoded so that next chunk will be empty (last chunk)
     */
    private boolean isLastChunk;
    /**
     * Last chunk already sent
     */
    private boolean isLastChunkSent;
    /**
     * The current FileUpload that is currently in encode process
     */
    private FileUpload currentFileUpload;
    /**
     * While adding a FileUpload, is the multipart currently in Mixed Mode
     */
    private boolean duringMixedMode;

    /**
     * Global Body size
     */
    private long globalBodySize;

    /**
     * True if this request is a Multipart request
     * @return True if this request is a Multipart request
     */
    public boolean isMultipart() {
        return isMultipart;
    }

    /**
     * Init the delimiter for Global Part (Data).
     */
    private void initDataMultipart() {
        multipartDataBoundary = getNewMultipartDelimiter();
    }

    /**
     * Init the delimiter for Mixed Part (Mixed).
     */
    private void initMixedMultipart() {
        multipartMixedBoundary = getNewMultipartDelimiter();
    }

    /**
     *
     * @return a newly generated Delimiter (either for DATA or MIXED)
     */
    private static String getNewMultipartDelimiter() {
        // construct a generated delimiter
        Random random = new Random();
        return Long.toHexString(random.nextLong()).toLowerCase();
    }

    /**
     * This method returns a List of all InterfaceHttpData from body part.<br>

     * @return the list of InterfaceHttpData from Body part
     */
    public List<InterfaceHttpData> getBodyListAttributes() {
        return bodyListDatas;
    }

    /**
     * Set the Body HttpDatas list
     * @throws NullPointerException for datas
     * @throws ErrorDataEncoderException if the encoding is in error or if the finalize were already done
     */
    public void setBodyHttpDatas(List<InterfaceHttpData> datas)
            throws ErrorDataEncoderException {
        if (datas == null) {
            throw new NullPointerException("datas");
        }
        globalBodySize = 0;
        bodyListDatas.clear();
        currentFileUpload = null;
        duringMixedMode = false;
        multipartHttpDatas.clear();
        for (InterfaceHttpData data: datas) {
            addBodyHttpData(data);
        }
    }

    /**
     * Add a simple attribute in the body as Name=Value
     * @param name name of the parameter
     * @param value the value of the parameter
     * @throws NullPointerException for name
     * @throws ErrorDataEncoderException if the encoding is in error or if the finalize were already done
     */
    public void addBodyAttribute(String name, String value)
    throws ErrorDataEncoderException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        String svalue = value;
        if (value == null) {
            svalue = "";
        }
        Attribute data = factory.createAttribute(request, name, svalue);
        addBodyHttpData(data);
    }

    /**
     * Add a file as a FileUpload
     * @param name the name of the parameter
     * @param file the file to be uploaded (if not Multipart mode, only the filename will be included)
     * @param contentType the associated contentType for the File
     * @param isText True if this file should be transmitted in Text format (else binary)
     * @throws NullPointerException for name and file
     * @throws ErrorDataEncoderException if the encoding is in error or if the finalize were already done
     */
    public void addBodyFileUpload(String name, File file, String contentType, boolean isText)
    throws ErrorDataEncoderException {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (file == null) {
            throw new NullPointerException("file");
        }
        String scontentType = contentType;
        String contentTransferEncoding = null;
        if (contentType == null) {
            if (isText) {
                scontentType = HttpPostBodyUtil.DEFAULT_TEXT_CONTENT_TYPE;
            } else {
                scontentType = HttpPostBodyUtil.DEFAULT_BINARY_CONTENT_TYPE;
            }
        }
        if (!isText) {
            contentTransferEncoding = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();
        }
        FileUpload fileUpload = factory.createFileUpload(request, name, file.getName(),
                scontentType, contentTransferEncoding, null, file.length());
        try {
            fileUpload.setContent(file);
        } catch (IOException e) {
            throw new ErrorDataEncoderException(e);
        }
        addBodyHttpData(fileUpload);
    }

    /**
     * Add a series of Files associated with one File parameter
     * @param name the name of the parameter
     * @param file the array of files
     * @param contentType the array of content Types associated with each file
     * @param isText the array of isText attribute (False meaning binary mode) for each file
     * @throws NullPointerException also throws if array have different sizes
     * @throws ErrorDataEncoderException if the encoding is in error or if the finalize were already done
     */
    public void addBodyFileUploads(String name, File[] file, String[] contentType, boolean[] isText)
    throws ErrorDataEncoderException {
        if (file.length != contentType.length && file.length != isText.length) {
            throw new NullPointerException("Different array length");
        }
        for (int i = 0; i < file.length; i++) {
            addBodyFileUpload(name, file[i], contentType[i], isText[i]);
        }
    }

    /**
     * Add the InterfaceHttpData to the Body list
     * @throws NullPointerException for data
     * @throws ErrorDataEncoderException if the encoding is in error or if the finalize were already done
     */
    public void addBodyHttpData(InterfaceHttpData data)
    throws ErrorDataEncoderException {
        if (headerFinalized) {
            throw new ErrorDataEncoderException("Cannot add value once finalized");
        }
        if (data == null) {
            throw new NullPointerException("data");
        }
        bodyListDatas.add(data);
        if (! isMultipart) {
            if (data instanceof Attribute) {
                Attribute attribute = (Attribute) data;
                try {
                    // name=value& with encoded name and attribute
                    String key = encodeAttribute(attribute.getName(), charset);
                    String value = encodeAttribute(attribute.getValue(), charset);
                    Attribute newattribute = factory.createAttribute(request, key, value);
                    multipartHttpDatas.add(newattribute);
                    globalBodySize += newattribute.getName().length() + 1 +
                        newattribute.length() + 1;
                } catch (IOException e) {
                    throw new ErrorDataEncoderException(e);
                }
            } else if (data instanceof FileUpload) {
                // since not Multipart, only name=filename => Attribute
                FileUpload fileUpload = (FileUpload) data;
                // name=filename& with encoded name and filename
                String key = encodeAttribute(fileUpload.getName(), charset);
                String value = encodeAttribute(fileUpload.getFilename(), charset);
                Attribute newattribute = factory.createAttribute(request, key, value);
                multipartHttpDatas.add(newattribute);
                globalBodySize += newattribute.getName().length() + 1 +
                    newattribute.length() + 1;
            }
            return;
        }
        /*
         * Logic:
         * if not Attribute:
         *      add Data to body list
         *      if (duringMixedMode)
         *          add endmixedmultipart delimiter
         *          currentFileUpload = null
         *          duringMixedMode = false;
         *      add multipart delimiter, multipart body header and Data to multipart list
         *      reset currentFileUpload, duringMixedMode
         * if FileUpload: take care of multiple file for one field => mixed mode
         *      if (duringMixeMode)
         *          if (currentFileUpload.name == data.name)
         *              add mixedmultipart delimiter, mixedmultipart body header and Data to multipart list
         *          else
         *              add endmixedmultipart delimiter, multipart body header and Data to multipart list
         *              currentFileUpload = data
         *              duringMixedMode = false;
         *      else
         *          if (currentFileUpload.name == data.name && !EncoderMode.HTML5)
         *              change multipart body header of previous file into multipart list to
         *                      mixedmultipart start, mixedmultipart body header
         *              add mixedmultipart delimiter, mixedmultipart body header and Data to multipart list
         *              duringMixedMode = true
         *          else
         *              add multipart delimiter, multipart body header and Data to multipart list
         *              currentFileUpload = data
         *              duringMixedMode = false;
         * Do not add last delimiter! Could be:
         * if duringmixedmode: endmixedmultipart + endmultipart
         * else only endmultipart
         */
        if (data instanceof Attribute) {
            if (duringMixedMode) {
                InternalAttribute internal = new InternalAttribute(charset);
                internal.addValue("\r\n--" + multipartMixedBoundary + "--");
                multipartHttpDatas.add(internal);
                multipartMixedBoundary = null;
                currentFileUpload = null;
                duringMixedMode = false;
            }
            InternalAttribute internal = new InternalAttribute(charset);
            if (!multipartHttpDatas.isEmpty()) {
                // previously a data field so CRLF
                internal.addValue("\r\n");
            }
            internal.addValue("--" + multipartDataBoundary + "\r\n");
            // content-disposition: form-data; name="field1"
            Attribute attribute = (Attribute) data;
            internal.addValue(HttpPostBodyUtil.CONTENT_DISPOSITION + ": " +
                    HttpPostBodyUtil.FORM_DATA + "; " +
                    HttpPostBodyUtil.NAME + "=\"" + attribute.getName() + "\"\r\n");
            Charset localcharset = attribute.getCharset();
            if (localcharset != null) {
                // Content-Type: charset=charset
                internal.addValue(HttpHeaders.Names.CONTENT_TYPE + ": " +
                        HttpPostBodyUtil.DEFAULT_TEXT_CONTENT_TYPE + "; " +
                        HttpHeaders.Values.CHARSET + '=' + localcharset.name() + "\r\n");
            }
            // CRLF between body header and data
            internal.addValue("\r\n");
            multipartHttpDatas.add(internal);
            multipartHttpDatas.add(data);
            globalBodySize += attribute.length() + internal.size();
        } else if (data instanceof FileUpload) {
            FileUpload fileUpload = (FileUpload) data;
            InternalAttribute internal = new InternalAttribute(charset);
            if (!multipartHttpDatas.isEmpty()) {
                // previously a data field so CRLF
                internal.addValue("\r\n");
            }
            boolean localMixed;
            if (duringMixedMode) {
                if (currentFileUpload != null &&
                        currentFileUpload.getName().equals(fileUpload.getName())) {
                    // continue a mixed mode

                    localMixed = true;
                } else {
                    // end a mixed mode

                    // add endmixedmultipart delimiter, multipart body header and
                    // Data to multipart list
                    internal.addValue("--" + multipartMixedBoundary + "--");
                    multipartHttpDatas.add(internal);
                    multipartMixedBoundary = null;
                    // start a new one (could be replaced if mixed start again from here
                    internal = new InternalAttribute(charset);
                    internal.addValue("\r\n");
                    localMixed = false;
                    // new currentFileUpload and no more in Mixed mode
                    currentFileUpload = fileUpload;
                    duringMixedMode = false;
                }
            } else {
                if (encoderMode != EncoderMode.HTML5 && currentFileUpload != null &&
                        currentFileUpload.getName().equals(fileUpload.getName())) {
                    // create a new mixed mode (from previous file)

                    // change multipart body header of previous file into multipart list to
                    // mixedmultipart start, mixedmultipart body header

                    // change Internal (size()-2 position in multipartHttpDatas)
                    // from (line starting with *)
                    // --AaB03x
                    // * Content-Disposition: form-data; name="files"; filename="file1.txt"
                    // Content-Type: text/plain
                    // to (lines starting with *)
                    // --AaB03x
                    // * Content-Disposition: form-data; name="files"
                    // * Content-Type: multipart/mixed; boundary=BbC04y
                    // *
                    // * --BbC04y
                    // * Content-Disposition: attachment; filename="file1.txt"
                    // Content-Type: text/plain
                    initMixedMultipart();
                    InternalAttribute pastAttribute =
                        (InternalAttribute) multipartHttpDatas.get(multipartHttpDatas.size() - 2);
                    // remove past size
                    globalBodySize -= pastAttribute.size();
                    StringBuilder replacement = new StringBuilder(
                            139 + multipartDataBoundary.length() + multipartMixedBoundary.length() * 2 +
                                    fileUpload.getFilename().length() + fileUpload.getName().length());

                    replacement.append("--");
                    replacement.append(multipartDataBoundary);
                    replacement.append("\r\n");

                    replacement.append(HttpPostBodyUtil.CONTENT_DISPOSITION);
                    replacement.append(": ");
                    replacement.append(HttpPostBodyUtil.FORM_DATA);
                    replacement.append("; ");
                    replacement.append(HttpPostBodyUtil.NAME);
                    replacement.append("=\"");
                    replacement.append(fileUpload.getName());
                    replacement.append("\"\r\n");

                    replacement.append(HttpHeaders.Names.CONTENT_TYPE);
                    replacement.append(": ");
                    replacement.append(HttpPostBodyUtil.MULTIPART_MIXED);
                    replacement.append("; ");
                    replacement.append(HttpHeaders.Values.BOUNDARY);
                    replacement.append('=');
                    replacement.append(multipartMixedBoundary);
                    replacement.append("\r\n\r\n");

                    replacement.append("--");
                    replacement.append(multipartMixedBoundary);
                    replacement.append("\r\n");

                    replacement.append(HttpPostBodyUtil.CONTENT_DISPOSITION);
                    replacement.append(": ");
                    replacement.append(HttpPostBodyUtil.ATTACHMENT);
                    replacement.append("; ");
                    replacement.append(HttpPostBodyUtil.FILENAME);
                    replacement.append("=\"");
                    replacement.append(fileUpload.getFilename());
                    replacement.append("\"\r\n");

                    pastAttribute.setValue(replacement.toString(), 1);
                    pastAttribute.setValue("", 2);

                    // update past size
                    globalBodySize += pastAttribute.size();

                    // now continue
                    // add mixedmultipart delimiter, mixedmultipart body header and
                    // Data to multipart list
                    localMixed = true;
                    duringMixedMode = true;
                } else {
                    // a simple new multipart
                    //add multipart delimiter, multipart body header and Data to multipart list
                    localMixed = false;
                    currentFileUpload = fileUpload;
                    duringMixedMode = false;
                }
            }

            if (localMixed) {
                // add mixedmultipart delimiter, mixedmultipart body header and
                // Data to multipart list
                internal.addValue("--" + multipartMixedBoundary + "\r\n");
                // Content-Disposition: attachment; filename="file1.txt"
                internal.addValue(HttpPostBodyUtil.CONTENT_DISPOSITION + ": " +
                        HttpPostBodyUtil.ATTACHMENT + "; " + HttpPostBodyUtil.FILENAME + "=\"" +
                        fileUpload.getFilename() +
                        "\"\r\n");
            } else {
                internal.addValue("--" + multipartDataBoundary + "\r\n");
                // Content-Disposition: form-data; name="files"; filename="file1.txt"
                internal.addValue(HttpPostBodyUtil.CONTENT_DISPOSITION + ": " +
                        HttpPostBodyUtil.FORM_DATA + "; " + HttpPostBodyUtil.NAME + "=\"" +
                        fileUpload.getName() + "\"; " +
                        HttpPostBodyUtil.FILENAME + "=\"" +
                        fileUpload.getFilename() +
                        "\"\r\n");
            }
            // Content-Type: image/gif
            // Content-Type: text/plain; charset=ISO-8859-1
            // Content-Transfer-Encoding: binary
            internal.addValue(HttpHeaders.Names.CONTENT_TYPE + ": " +
                    fileUpload.getContentType());
            String contentTransferEncoding = fileUpload.getContentTransferEncoding();
            if (contentTransferEncoding != null &&
                    contentTransferEncoding.equals(
                            HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) {
                internal.addValue("\r\n" + HttpHeaders.Names.CONTENT_TRANSFER_ENCODING +
                        ": " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() +
                        "\r\n\r\n");
            } else if (fileUpload.getCharset() != null) {
                internal.addValue("; " + HttpHeaders.Values.CHARSET + '=' +
                        fileUpload.getCharset().name() + "\r\n\r\n");
            } else {
                internal.addValue("\r\n\r\n");
            }
            multipartHttpDatas.add(internal);
            multipartHttpDatas.add(data);
            globalBodySize += fileUpload.length() + internal.size();
        }
    }

    /**
     * Iterator to be used when encoding will be called chunk after chunk
     */
    private ListIterator<InterfaceHttpData> iterator;

    /**
     * Finalize the request by preparing the Header in the request and
     * returns the request ready to be sent.<br>
     * Once finalized, no data must be added.<br>
     * If the request does not need chunk (isChunked() == false),
     * this request is the only object to send to
     * the remote server.
     *
     * @return the request object (chunked or not according to size of body)
     * @throws ErrorDataEncoderException if the encoding is in error or if the finalize were already done
     */
    public HttpRequest finalizeRequest() throws ErrorDataEncoderException {
        HttpHeaders headers = request.headers();
        // Finalize the multipartHttpDatas
        if (! headerFinalized) {
            if (isMultipart) {
                InternalAttribute internal = new InternalAttribute(charset);
                if (duringMixedMode) {
                    internal.addValue("\r\n--" + multipartMixedBoundary + "--");
                }
                internal.addValue("\r\n--" + multipartDataBoundary + "--\r\n");
                multipartHttpDatas.add(internal);
                multipartMixedBoundary = null;
                currentFileUpload = null;
                duringMixedMode = false;
                globalBodySize += internal.size();
            }
            headerFinalized = true;
        } else {
            throw new ErrorDataEncoderException("Header already encoded");
        }
        List<String> contentTypes = headers.getAll(HttpHeaders.Names.CONTENT_TYPE);
        List<String> transferEncoding = headers.getAll(HttpHeaders.Names.TRANSFER_ENCODING);
        if (contentTypes != null) {
            headers.remove(HttpHeaders.Names.CONTENT_TYPE);
            for (String contentType: contentTypes) {
                // "multipart/form-data; boundary=--89421926422648"
                String lowercased = contentType.toLowerCase();
                if (lowercased.startsWith(HttpHeaders.Values.MULTIPART_FORM_DATA) ||
                    lowercased.startsWith(HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED)) {
                    // ignore
                } else {
                    headers.add(HttpHeaders.Names.CONTENT_TYPE, contentType);
                }
            }
        }
        if (isMultipart) {
            String value = HttpHeaders.Values.MULTIPART_FORM_DATA + "; " +
                HttpHeaders.Values.BOUNDARY + '=' + multipartDataBoundary;
            headers.add(HttpHeaders.Names.CONTENT_TYPE, value);
        } else {
            // Not multipart
            headers.add(HttpHeaders.Names.CONTENT_TYPE,
                    HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED);
        }
        // Now consider size for chunk or not
        long realSize = globalBodySize;
        if (isMultipart) {
            iterator = multipartHttpDatas.listIterator();
        } else {
            realSize -= 1; // last '&' removed
            iterator = multipartHttpDatas.listIterator();
        }
        headers.set(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(realSize));
        if (realSize > HttpPostBodyUtil.chunkSize || isMultipart) {
            isChunked = true;
            if (transferEncoding != null) {
                headers.remove(HttpHeaders.Names.TRANSFER_ENCODING);
                for (String v: transferEncoding) {
                    if (v.equalsIgnoreCase(HttpHeaders.Values.CHUNKED)) {
                        // ignore
                    } else {
                        headers.add(HttpHeaders.Names.TRANSFER_ENCODING, v);
                    }
                }
            }
            headers.add(HttpHeaders.Names.TRANSFER_ENCODING, HttpHeaders.Values.CHUNKED);
            request.setContent(ChannelBuffers.EMPTY_BUFFER);
        } else {
            // get the only one body and set it to the request
            HttpChunk chunk = nextChunk();
            request.setContent(chunk.getContent());
        }
        return request;
    }

    /**
     * @return True if the request is by Chunk
     */
    public boolean isChunked() {
        return isChunked;
    }

    /**
     * Encode one attribute
     * @return the encoded attribute
     * @throws ErrorDataEncoderException if the encoding is in error
     */
    private String encodeAttribute(String s, Charset charset)
            throws ErrorDataEncoderException {
        if (s == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(s, charset.name());
            if (encoderMode == EncoderMode.RFC3986) {
                for (Map.Entry<Pattern, String> entry : percentEncodings.entrySet()) {
                    String replacement = entry.getValue();
                    encoded = entry.getKey().matcher(encoded).replaceAll(replacement);
                }
            }
            return encoded;
        } catch (UnsupportedEncodingException e) {
            throw new ErrorDataEncoderException(charset.name(), e);
        }
    }

    /**
     * The ChannelBuffer currently used by the encoder
     */
    private ChannelBuffer currentBuffer;
    /**
     * The current InterfaceHttpData to encode (used if more chunks are available)
     */
    private InterfaceHttpData currentData;
    /**
     * If not multipart, does the currentBuffer stands for the Key or for the Value
     */
    private boolean isKey = true;

    /**
     *
     * @return the next ChannelBuffer to send as a HttpChunk and modifying currentBuffer
     * accordingly
     */
    private ChannelBuffer fillChannelBuffer() {
        int length = currentBuffer.readableBytes();
        if (length > HttpPostBodyUtil.chunkSize) {
            ChannelBuffer slice =
                currentBuffer.slice(currentBuffer.readerIndex(), HttpPostBodyUtil.chunkSize);
            currentBuffer.skipBytes(HttpPostBodyUtil.chunkSize);
            return slice;
        } else {
            // to continue
            ChannelBuffer slice = currentBuffer;
            currentBuffer = null;
            return slice;
        }
    }

    /**
     * From the current context (currentBuffer and currentData), returns the next HttpChunk
     * (if possible) trying to get sizeleft bytes more into the currentBuffer.
     * This is the Multipart version.
     *
     * @param sizeleft the number of bytes to try to get from currentData
     * @return the next HttpChunk or null if not enough bytes were found
     * @throws ErrorDataEncoderException if the encoding is in error
     */
    private HttpChunk encodeNextChunkMultipart(int sizeleft) throws ErrorDataEncoderException {
        if (currentData == null) {
            return null;
        }
        ChannelBuffer buffer;
        if (currentData instanceof InternalAttribute) {

            buffer = ((InternalAttribute) currentData).toChannelBuffer();
            currentData = null;
        } else {
            if (currentData instanceof Attribute) {
                try {
                    buffer = ((Attribute) currentData).getChunk(sizeleft);
                } catch (IOException e) {
                    throw new ErrorDataEncoderException(e);
                }
            } else {
                try {
                    buffer = ((HttpData) currentData).getChunk(sizeleft);
                } catch (IOException e) {
                    throw new ErrorDataEncoderException(e);
                }
            }
            if (buffer.capacity() == 0) {
                // end for current InterfaceHttpData, need more data
                currentData = null;
                return null;
            }
        }
        if (currentBuffer == null) {
            currentBuffer = buffer;
        } else {
            currentBuffer = ChannelBuffers.wrappedBuffer(currentBuffer,
                buffer);
        }
        if (currentBuffer.readableBytes() < HttpPostBodyUtil.chunkSize) {
            currentData = null;
            return null;
        }
        buffer = fillChannelBuffer();
        return new DefaultHttpChunk(buffer);
    }

    /**
     * From the current context (currentBuffer and currentData), returns the next HttpChunk
     * (if possible) trying to get sizeleft bytes more into the currentBuffer.
     * This is the UrlEncoded version.
     *
     * @param sizeleft the number of bytes to try to get from currentData
     * @return the next HttpChunk or null if not enough bytes were found
     * @throws ErrorDataEncoderException if the encoding is in error
     */
    private HttpChunk encodeNextChunkUrlEncoded(int sizeleft) throws ErrorDataEncoderException {
        if (currentData == null) {
            return null;
        }
        int size = sizeleft;
        ChannelBuffer buffer;
        if (isKey) {
            // get name
            String key = currentData.getName();
            buffer = ChannelBuffers.wrappedBuffer(key.getBytes());
            isKey = false;
            if (currentBuffer == null) {
                currentBuffer = ChannelBuffers.wrappedBuffer(
                        buffer, ChannelBuffers.wrappedBuffer("=".getBytes()));
                //continue
                size -= buffer.readableBytes() + 1;
            } else {
                currentBuffer = ChannelBuffers.wrappedBuffer(currentBuffer,
                    buffer, ChannelBuffers.wrappedBuffer("=".getBytes()));
                //continue
                size -= buffer.readableBytes() + 1;
            }
            if (currentBuffer.readableBytes() >= HttpPostBodyUtil.chunkSize) {
                buffer = fillChannelBuffer();
                return new DefaultHttpChunk(buffer);
            }
        }
        try {
            buffer = ((HttpData) currentData).getChunk(size);
        } catch (IOException e) {
            throw new ErrorDataEncoderException(e);
        }
        ChannelBuffer delimiter = null;
        if (buffer.readableBytes() < size) {
            // delimiter
            isKey = true;
            delimiter = iterator.hasNext() ?
                    ChannelBuffers.wrappedBuffer("&".getBytes()) :
                        null;
        }
        if (buffer.capacity() == 0) {
            // end for current InterfaceHttpData, need potentially more data
            currentData = null;
            if (currentBuffer == null) {
                currentBuffer = delimiter;
            } else {
                if (delimiter != null) {
                    currentBuffer = ChannelBuffers.wrappedBuffer(currentBuffer,
                        delimiter);
                }
            }
            if (currentBuffer.readableBytes() >= HttpPostBodyUtil.chunkSize) {
                buffer = fillChannelBuffer();
                return new DefaultHttpChunk(buffer);
            }
            return null;
        }
        if (currentBuffer == null) {
            if (delimiter != null) {
                currentBuffer = ChannelBuffers.wrappedBuffer(buffer,
                    delimiter);
            } else {
                currentBuffer = buffer;
            }
        } else {
            if (delimiter != null) {
                currentBuffer = ChannelBuffers.wrappedBuffer(currentBuffer,
                    buffer, delimiter);
            } else {
                currentBuffer = ChannelBuffers.wrappedBuffer(currentBuffer,
                        buffer);
            }
        }
        if (currentBuffer.readableBytes() < HttpPostBodyUtil.chunkSize) {
            // end for current InterfaceHttpData, need more data
            currentData = null;
            isKey = true;
            return null;
        }
        buffer = fillChannelBuffer();
        // size = 0
        return new DefaultHttpChunk(buffer);
    }

    public void close() throws Exception {
        //NO since the user can want to reuse (broadcast for instance) cleanFiles();
    }

    /**
     * Returns the next available HttpChunk. The caller is responsible to test if this chunk is the
     * last one (isLast()), in order to stop calling this method.
     *
     * @return the next available HttpChunk
     * @throws ErrorDataEncoderException if the encoding is in error
     */
    public HttpChunk nextChunk() throws ErrorDataEncoderException {
        if (isLastChunk) {
            isLastChunkSent = true;
            return new DefaultHttpChunk(ChannelBuffers.EMPTY_BUFFER);
        }
        ChannelBuffer buffer;
        int size = HttpPostBodyUtil.chunkSize;
        // first test if previous buffer is not empty
        if (currentBuffer != null) {
            size -= currentBuffer.readableBytes();
        }
        if (size <= 0) {
            //NextChunk from buffer
            buffer = fillChannelBuffer();
            return new DefaultHttpChunk(buffer);
        }
        // size > 0
        if (currentData != null) {
            // continue to read data
            if (isMultipart) {
                HttpChunk chunk = encodeNextChunkMultipart(size);
                if (chunk != null) {
                    return chunk;
                }
            } else {
                HttpChunk chunk = encodeNextChunkUrlEncoded(size);
                if (chunk != null) {
                    //NextChunk Url from currentData
                    return chunk;
                }
            }
            size = HttpPostBodyUtil.chunkSize - currentBuffer.readableBytes();
        }
        if (! iterator.hasNext()) {
            isLastChunk = true;
            //NextChunk as last non empty from buffer
            buffer = currentBuffer;
            currentBuffer = null;
            return new DefaultHttpChunk(buffer);
        }
        while (size > 0 && iterator.hasNext()) {
            currentData = iterator.next();
            HttpChunk chunk;
            if (isMultipart) {
                chunk = encodeNextChunkMultipart(size);
            } else {
                chunk = encodeNextChunkUrlEncoded(size);
            }
            if (chunk == null) {
                // not enough
                size = HttpPostBodyUtil.chunkSize - currentBuffer.readableBytes();
                continue;
            }
            //NextChunk from data
            return chunk;
        }
        // end since no more data
        isLastChunk = true;
        if (currentBuffer == null) {
            isLastChunkSent = true;
            //LastChunk with no more data
            return new DefaultHttpChunk(ChannelBuffers.EMPTY_BUFFER);
        }
        //Previous LastChunk with no more data
        buffer = currentBuffer;
        currentBuffer = null;
        return new DefaultHttpChunk(buffer);
    }

    public boolean isEndOfInput() throws Exception {
        return isLastChunkSent;
    }

    public boolean hasNextChunk() throws Exception {
      return !isLastChunkSent;
    }

    /**
     * Exception when an error occurs while encoding
     */
    public static class ErrorDataEncoderException extends Exception {
        private static final long serialVersionUID = 5020247425493164465L;

        public ErrorDataEncoderException() {
        }

        public ErrorDataEncoderException(String msg) {
            super(msg);
        }

        public ErrorDataEncoderException(Throwable cause) {
            super(cause);
        }

        public ErrorDataEncoderException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
