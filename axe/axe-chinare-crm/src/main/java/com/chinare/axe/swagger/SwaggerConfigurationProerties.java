package com.chinare.axe.swagger;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;

/**
 * @author kerbores
 *
 */
@ConfigurationProperties("swagger")
public class SwaggerConfigurationProerties {

    /**
     * 是否开启swagger
     **/
    private Boolean enabled;

    /**
     * 标题
     **/
    private String title = "";
    /**
     * 描述
     **/
    private String description = "";
    /**
     * 版本
     **/
    private String version = "";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * 忽略的参数类型
     **/
    private List<Class<?>> ignoredParameterTypes = Lists.newArrayList();

    private Contact contact = new Contact();

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = Lists.newArrayList();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = Lists.newArrayList();

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = Maps.newConcurrentMap();

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;

    /**
     * 页面功能配置
     **/
    private UiConfig uiConfig = new UiConfig();

    /**
     * 是否使用默认预定义的响应消息 ，默认 true
     **/
    private Boolean applyDefaultResponseMessages = true;

    /**
     * 全局响应消息
     **/
    private GlobalResponseMessage globalResponseMessage;

    /**
     * 全局统一鉴权配置
     **/
    private Authorization authorization = new Authorization();

    public static class GlobalOperationParameter {
        /**
         * 参数名
         **/
        private String name;

        /**
         * 描述信息
         **/
        private String description;

        /**
         * 指定参数类型
         **/
        private String modelRef;

        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType;

        /**
         * 参数是否必须传
         **/
        private String required;

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description
         *            the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the modelRef
         */
        public String getModelRef() {
            return modelRef;
        }

        /**
         * @param modelRef
         *            the modelRef to set
         */
        public void setModelRef(String modelRef) {
            this.modelRef = modelRef;
        }

        /**
         * @return the parameterType
         */
        public String getParameterType() {
            return parameterType;
        }

        /**
         * @param parameterType
         *            the parameterType to set
         */
        public void setParameterType(String parameterType) {
            this.parameterType = parameterType;
        }

        /**
         * @return the required
         */
        public String getRequired() {
            return required;
        }

        /**
         * @param required
         *            the required to set
         */
        public void setRequired(String required) {
            this.required = required;
        }

    }

    public static class DocketInfo {

        /**
         * 标题
         **/
        private String title = "";
        /**
         * 描述
         **/
        private String description = "";
        /**
         * 版本
         **/
        private String version = "";
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";

        private Contact contact = new Contact();

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = Lists.newArrayList();
        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = Lists.newArrayList();

        private List<GlobalOperationParameter> globalOperationParameters;

        /**
         * 忽略的参数类型
         **/
        private List<Class<?>> ignoredParameterTypes = Lists.newArrayList();

        /**
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title
         *            the title to set
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description
         *            the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the version
         */
        public String getVersion() {
            return version;
        }

        /**
         * @param version
         *            the version to set
         */
        public void setVersion(String version) {
            this.version = version;
        }

        /**
         * @return the license
         */
        public String getLicense() {
            return license;
        }

        /**
         * @param license
         *            the license to set
         */
        public void setLicense(String license) {
            this.license = license;
        }

        /**
         * @return the licenseUrl
         */
        public String getLicenseUrl() {
            return licenseUrl;
        }

        /**
         * @param licenseUrl
         *            the licenseUrl to set
         */
        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        /**
         * @return the termsOfServiceUrl
         */
        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        /**
         * @param termsOfServiceUrl
         *            the termsOfServiceUrl to set
         */
        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        /**
         * @return the contact
         */
        public Contact getContact() {
            return contact;
        }

        /**
         * @param contact
         *            the contact to set
         */
        public void setContact(Contact contact) {
            this.contact = contact;
        }

        /**
         * @return the basePackage
         */
        public String getBasePackage() {
            return basePackage;
        }

        /**
         * @param basePackage
         *            the basePackage to set
         */
        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }

        /**
         * @return the basePath
         */
        public List<String> getBasePath() {
            return basePath;
        }

        /**
         * @param basePath
         *            the basePath to set
         */
        public void setBasePath(List<String> basePath) {
            this.basePath = basePath;
        }

        /**
         * @return the excludePath
         */
        public List<String> getExcludePath() {
            return excludePath;
        }

        /**
         * @param excludePath
         *            the excludePath to set
         */
        public void setExcludePath(List<String> excludePath) {
            this.excludePath = excludePath;
        }

        /**
         * @return the globalOperationParameters
         */
        public List<GlobalOperationParameter> getGlobalOperationParameters() {
            return globalOperationParameters;
        }

        /**
         * @param globalOperationParameters
         *            the globalOperationParameters to set
         */
        public void setGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters) {
            this.globalOperationParameters = globalOperationParameters;
        }

        /**
         * @return the ignoredParameterTypes
         */
        public List<Class<?>> getIgnoredParameterTypes() {
            return ignoredParameterTypes;
        }

        /**
         * @param ignoredParameterTypes
         *            the ignoredParameterTypes to set
         */
        public void setIgnoredParameterTypes(List<Class<?>> ignoredParameterTypes) {
            this.ignoredParameterTypes = ignoredParameterTypes;
        }

    }

    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url
         *            the url to set
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return the email
         */
        public String getEmail() {
            return email;
        }

        /**
         * @param email
         *            the email to set
         */
        public void setEmail(String email) {
            this.email = email;
        }

    }

    public static class GlobalResponseMessage {

        /**
         * POST 响应消息体
         **/
        List<GlobalResponseMessageBody> post = Lists.newArrayList();

        /**
         * GET 响应消息体
         **/
        List<GlobalResponseMessageBody> get = Lists.newArrayList();

        /**
         * PUT 响应消息体
         **/
        List<GlobalResponseMessageBody> put = Lists.newArrayList();

        /**
         * PATCH 响应消息体
         **/
        List<GlobalResponseMessageBody> patch = Lists.newArrayList();

        /**
         * DELETE 响应消息体
         **/
        List<GlobalResponseMessageBody> delete = Lists.newArrayList();

        /**
         * HEAD 响应消息体
         **/
        List<GlobalResponseMessageBody> head = Lists.newArrayList();

        /**
         * OPTIONS 响应消息体
         **/
        List<GlobalResponseMessageBody> options = Lists.newArrayList();

        /**
         * TRACE 响应消息体
         **/
        List<GlobalResponseMessageBody> trace = Lists.newArrayList();

        /**
         * @return the post
         */
        public List<GlobalResponseMessageBody> getPost() {
            return post;
        }

        /**
         * @param post
         *            the post to set
         */
        public void setPost(List<GlobalResponseMessageBody> post) {
            this.post = post;
        }

        /**
         * @return the get
         */
        public List<GlobalResponseMessageBody> getGet() {
            return get;
        }

        /**
         * @param get
         *            the get to set
         */
        public void setGet(List<GlobalResponseMessageBody> get) {
            this.get = get;
        }

        /**
         * @return the put
         */
        public List<GlobalResponseMessageBody> getPut() {
            return put;
        }

        /**
         * @param put
         *            the put to set
         */
        public void setPut(List<GlobalResponseMessageBody> put) {
            this.put = put;
        }

        /**
         * @return the patch
         */
        public List<GlobalResponseMessageBody> getPatch() {
            return patch;
        }

        /**
         * @param patch
         *            the patch to set
         */
        public void setPatch(List<GlobalResponseMessageBody> patch) {
            this.patch = patch;
        }

        /**
         * @return the delete
         */
        public List<GlobalResponseMessageBody> getDelete() {
            return delete;
        }

        /**
         * @param delete
         *            the delete to set
         */
        public void setDelete(List<GlobalResponseMessageBody> delete) {
            this.delete = delete;
        }

        /**
         * @return the head
         */
        public List<GlobalResponseMessageBody> getHead() {
            return head;
        }

        /**
         * @param head
         *            the head to set
         */
        public void setHead(List<GlobalResponseMessageBody> head) {
            this.head = head;
        }

        /**
         * @return the options
         */
        public List<GlobalResponseMessageBody> getOptions() {
            return options;
        }

        /**
         * @param options
         *            the options to set
         */
        public void setOptions(List<GlobalResponseMessageBody> options) {
            this.options = options;
        }

        /**
         * @return the trace
         */
        public List<GlobalResponseMessageBody> getTrace() {
            return trace;
        }

        /**
         * @param trace
         *            the trace to set
         */
        public void setTrace(List<GlobalResponseMessageBody> trace) {
            this.trace = trace;
        }

    }

    public static class GlobalResponseMessageBody {

        /**
         * 响应码
         **/
        private int code;

        /**
         * 响应消息
         **/
        private String message;

        /**
         * 响应体
         **/
        private String modelRef;

        /**
         * @return the code
         */
        public int getCode() {
            return code;
        }

        /**
         * @param code
         *            the code to set
         */
        public void setCode(int code) {
            this.code = code;
        }

        /**
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message
         *            the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * @return the modelRef
         */
        public String getModelRef() {
            return modelRef;
        }

        /**
         * @param modelRef
         *            the modelRef to set
         */
        public void setModelRef(String modelRef) {
            this.modelRef = modelRef;
        }

    }

    public static class UiConfig {

        private String apiSorter = "alpha";

        /**
         * 是否启用json编辑器
         **/
        private Boolean jsonEditor = false;
        /**
         * 是否显示请求头信息
         **/
        private Boolean showRequestHeaders = true;
        /**
         * 支持页面提交的请求类型
         **/
        private String submitMethods = "get,post,put,delete,patch";
        /**
         * 请求超时时间
         **/
        private Long requestTimeout = 10000L;

        private Boolean deepLinking;
        private Boolean displayOperationId;
        private Integer defaultModelsExpandDepth;
        private Integer defaultModelExpandDepth;
        private ModelRendering defaultModelRendering;

        /**
         * 是否显示请求耗时，默认false
         */
        private Boolean displayRequestDuration = true;
        /**
         * 可选 none | list
         */
        private DocExpansion docExpansion;
        /**
         * Boolean=false OR String
         */
        private Object filter;
        private Integer maxDisplayedTags;
        private OperationsSorter operationsSorter;
        private Boolean showExtensions;
        private TagsSorter tagsSorter;

        /**
         * Network
         */
        private String validatorUrl;

        /**
         * @return the apiSorter
         */
        public String getApiSorter() {
            return apiSorter;
        }

        /**
         * @param apiSorter
         *            the apiSorter to set
         */
        public void setApiSorter(String apiSorter) {
            this.apiSorter = apiSorter;
        }

        /**
         * @return the jsonEditor
         */
        public Boolean getJsonEditor() {
            return jsonEditor;
        }

        /**
         * @param jsonEditor
         *            the jsonEditor to set
         */
        public void setJsonEditor(Boolean jsonEditor) {
            this.jsonEditor = jsonEditor;
        }

        /**
         * @return the showRequestHeaders
         */
        public Boolean getShowRequestHeaders() {
            return showRequestHeaders;
        }

        /**
         * @param showRequestHeaders
         *            the showRequestHeaders to set
         */
        public void setShowRequestHeaders(Boolean showRequestHeaders) {
            this.showRequestHeaders = showRequestHeaders;
        }

        /**
         * @return the submitMethods
         */
        public String getSubmitMethods() {
            return submitMethods;
        }

        /**
         * @param submitMethods
         *            the submitMethods to set
         */
        public void setSubmitMethods(String submitMethods) {
            this.submitMethods = submitMethods;
        }

        /**
         * @return the requestTimeout
         */
        public Long getRequestTimeout() {
            return requestTimeout;
        }

        /**
         * @param requestTimeout
         *            the requestTimeout to set
         */
        public void setRequestTimeout(Long requestTimeout) {
            this.requestTimeout = requestTimeout;
        }

        /**
         * @return the deepLinking
         */
        public Boolean getDeepLinking() {
            return deepLinking;
        }

        /**
         * @param deepLinking
         *            the deepLinking to set
         */
        public void setDeepLinking(Boolean deepLinking) {
            this.deepLinking = deepLinking;
        }

        /**
         * @return the displayOperationId
         */
        public Boolean getDisplayOperationId() {
            return displayOperationId;
        }

        /**
         * @param displayOperationId
         *            the displayOperationId to set
         */
        public void setDisplayOperationId(Boolean displayOperationId) {
            this.displayOperationId = displayOperationId;
        }

        /**
         * @return the defaultModelsExpandDepth
         */
        public Integer getDefaultModelsExpandDepth() {
            return defaultModelsExpandDepth;
        }

        /**
         * @param defaultModelsExpandDepth
         *            the defaultModelsExpandDepth to set
         */
        public void setDefaultModelsExpandDepth(Integer defaultModelsExpandDepth) {
            this.defaultModelsExpandDepth = defaultModelsExpandDepth;
        }

        /**
         * @return the defaultModelExpandDepth
         */
        public Integer getDefaultModelExpandDepth() {
            return defaultModelExpandDepth;
        }

        /**
         * @param defaultModelExpandDepth
         *            the defaultModelExpandDepth to set
         */
        public void setDefaultModelExpandDepth(Integer defaultModelExpandDepth) {
            this.defaultModelExpandDepth = defaultModelExpandDepth;
        }

        /**
         * @return the defaultModelRendering
         */
        public ModelRendering getDefaultModelRendering() {
            return defaultModelRendering;
        }

        /**
         * @param defaultModelRendering
         *            the defaultModelRendering to set
         */
        public void setDefaultModelRendering(ModelRendering defaultModelRendering) {
            this.defaultModelRendering = defaultModelRendering;
        }

        /**
         * @return the displayRequestDuration
         */
        public Boolean getDisplayRequestDuration() {
            return displayRequestDuration;
        }

        /**
         * @param displayRequestDuration
         *            the displayRequestDuration to set
         */
        public void setDisplayRequestDuration(Boolean displayRequestDuration) {
            this.displayRequestDuration = displayRequestDuration;
        }

        /**
         * @return the docExpansion
         */
        public DocExpansion getDocExpansion() {
            return docExpansion;
        }

        /**
         * @param docExpansion
         *            the docExpansion to set
         */
        public void setDocExpansion(DocExpansion docExpansion) {
            this.docExpansion = docExpansion;
        }

        /**
         * @return the filter
         */
        public Object getFilter() {
            return filter;
        }

        /**
         * @param filter
         *            the filter to set
         */
        public void setFilter(Object filter) {
            this.filter = filter;
        }

        /**
         * @return the maxDisplayedTags
         */
        public Integer getMaxDisplayedTags() {
            return maxDisplayedTags;
        }

        /**
         * @param maxDisplayedTags
         *            the maxDisplayedTags to set
         */
        public void setMaxDisplayedTags(Integer maxDisplayedTags) {
            this.maxDisplayedTags = maxDisplayedTags;
        }

        /**
         * @return the operationsSorter
         */
        public OperationsSorter getOperationsSorter() {
            return operationsSorter;
        }

        /**
         * @param operationsSorter
         *            the operationsSorter to set
         */
        public void setOperationsSorter(OperationsSorter operationsSorter) {
            this.operationsSorter = operationsSorter;
        }

        /**
         * @return the showExtensions
         */
        public Boolean getShowExtensions() {
            return showExtensions;
        }

        /**
         * @param showExtensions
         *            the showExtensions to set
         */
        public void setShowExtensions(Boolean showExtensions) {
            this.showExtensions = showExtensions;
        }

        /**
         * @return the tagsSorter
         */
        public TagsSorter getTagsSorter() {
            return tagsSorter;
        }

        /**
         * @param tagsSorter
         *            the tagsSorter to set
         */
        public void setTagsSorter(TagsSorter tagsSorter) {
            this.tagsSorter = tagsSorter;
        }

        /**
         * @return the validatorUrl
         */
        public String getValidatorUrl() {
            return validatorUrl;
        }

        /**
         * @param validatorUrl
         *            the validatorUrl to set
         */
        public void setValidatorUrl(String validatorUrl) {
            this.validatorUrl = validatorUrl;
        }

    }

    /**
     * securitySchemes 支持方式之一 ApiKey
     */
    static class Authorization {

        /**
         * 鉴权策略ID，对应 SecurityReferences ID
         */
        private String name = "Authorization";

        /**
         * 鉴权传递的Header参数
         */
        private String keyName = "TOKEN";

        /**
         * 需要开启鉴权URL的正则
         */
        private String authRegex = "^.*$";

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the keyName
         */
        public String getKeyName() {
            return keyName;
        }

        /**
         * @param keyName
         *            the keyName to set
         */
        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        /**
         * @return the authRegex
         */
        public String getAuthRegex() {
            return authRegex;
        }

        /**
         * @param authRegex
         *            the authRegex to set
         */
        public void setAuthRegex(String authRegex) {
            this.authRegex = authRegex;
        }

    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the license
     */
    public String getLicense() {
        return license;
    }

    /**
     * @param license
     *            the license to set
     */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
     * @return the licenseUrl
     */
    public String getLicenseUrl() {
        return licenseUrl;
    }

    /**
     * @param licenseUrl
     *            the licenseUrl to set
     */
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    /**
     * @return the termsOfServiceUrl
     */
    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    /**
     * @param termsOfServiceUrl
     *            the termsOfServiceUrl to set
     */
    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    /**
     * @return the ignoredParameterTypes
     */
    public List<Class<?>> getIgnoredParameterTypes() {
        return ignoredParameterTypes;
    }

    /**
     * @param ignoredParameterTypes
     *            the ignoredParameterTypes to set
     */
    public void setIgnoredParameterTypes(List<Class<?>> ignoredParameterTypes) {
        this.ignoredParameterTypes = ignoredParameterTypes;
    }

    /**
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact
     *            the contact to set
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * @return the basePackage
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * @param basePackage
     *            the basePackage to set
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * @return the basePath
     */
    public List<String> getBasePath() {
        return basePath;
    }

    /**
     * @param basePath
     *            the basePath to set
     */
    public void setBasePath(List<String> basePath) {
        this.basePath = basePath;
    }

    /**
     * @return the excludePath
     */
    public List<String> getExcludePath() {
        return excludePath;
    }

    /**
     * @param excludePath
     *            the excludePath to set
     */
    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }

    /**
     * @return the docket
     */
    public Map<String, DocketInfo> getDocket() {
        return docket;
    }

    /**
     * @param docket
     *            the docket to set
     */
    public void setDocket(Map<String, DocketInfo> docket) {
        this.docket = docket;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the globalOperationParameters
     */
    public List<GlobalOperationParameter> getGlobalOperationParameters() {
        return globalOperationParameters;
    }

    /**
     * @param globalOperationParameters
     *            the globalOperationParameters to set
     */
    public void setGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters) {
        this.globalOperationParameters = globalOperationParameters;
    }

    /**
     * @return the uiConfig
     */
    public UiConfig getUiConfig() {
        return uiConfig;
    }

    /**
     * @param uiConfig
     *            the uiConfig to set
     */
    public void setUiConfig(UiConfig uiConfig) {
        this.uiConfig = uiConfig;
    }

    /**
     * @return the applyDefaultResponseMessages
     */
    public Boolean getApplyDefaultResponseMessages() {
        return applyDefaultResponseMessages;
    }

    /**
     * @param applyDefaultResponseMessages
     *            the applyDefaultResponseMessages to set
     */
    public void setApplyDefaultResponseMessages(Boolean applyDefaultResponseMessages) {
        this.applyDefaultResponseMessages = applyDefaultResponseMessages;
    }

    /**
     * @return the globalResponseMessage
     */
    public GlobalResponseMessage getGlobalResponseMessage() {
        return globalResponseMessage;
    }

    /**
     * @param globalResponseMessage
     *            the globalResponseMessage to set
     */
    public void setGlobalResponseMessage(GlobalResponseMessage globalResponseMessage) {
        this.globalResponseMessage = globalResponseMessage;
    }

    /**
     * @return the authorization
     */
    public Authorization getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization
     *            the authorization to set
     */
    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

}
