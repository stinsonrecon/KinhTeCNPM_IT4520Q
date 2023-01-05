//package vn.com.hust.config;
//
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@WebFilter(filterName = "myFilter", urlPatterns = { "*.html" })
//public class RequestFilter<T> implements Filter
//{
//	private static final Logger LOG = LoggerFactory.getLogger(RequestFilter.class);
//	final String IS_AUTHENTICATED = "isAuthenticated";
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException
//	{
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
//	{
//
//		HttpSession session = ((HttpServletRequest) request).getSession(true);
//		String requestPartent = null;
////		Boolean isAuthenticated = false;
//		try
//		{
//			String tmp = ((HttpServletRequest) request).getRequestURI();
//			if (!tmp.equals("/"))
//			{
//				requestPartent = tmp.split("/")[1];
//				if (requestPartent.equals("account") || requestPartent.equals("i18n") || requestPartent.equals("assets") || requestPartent.equals("js")
//						|| requestPartent.equals("views") || requestPartent.equals("images") || requestPartent.equals("favicon.ico")
//						|| requestPartent.equals("css") || requestPartent.equals("errorAuthorities") || requestPartent.equals("check"))
//				{
//					chain.doFilter(request, response);
//					return;
//				} else
//				{
////					checkSessionAuthenticated(isAuthenticated, session, request, response, chain);
//					checkSessionAuthenticated(true, session, request, response, chain);
//				}
//			} else
//			{
//				chain.doFilter(request, response);
//				return;
//			}
//		} catch (Exception e)
//		{
//			LOG.error(e.getMessage(), e);
////			request.getRequestDispatcher("/account/login").forward(request, response);
//			request.getRequestDispatcher("/testForm").forward(request, response);
//			return;
//		}
//
//	}
//
//	@Override
//	public void destroy()
//	{
//		// TODO Auto-generated method stub
//	}
//
//	public void checkSessionAuthenticated(Boolean isAuthenticated, HttpSession session, ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException
//	{
//		isAuthenticated = session.getAttribute(IS_AUTHENTICATED) != null ? (Boolean) session.getAttribute(IS_AUTHENTICATED) : false;
//
//		if (isAuthenticated.equals(true))
//		{
//			try
//			{
//				String uri = ((HttpServletRequest) request).getRequestURI();
////				String tranId = ((JSONObject) session.getAttribute("transEntity")).getString("transId");
//				Map<String, String> body = new HashMap<>();
//				body.put("assetCode", uri);
//				HttpHeaders headers = new HttpHeaders();
//				headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//				headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
////				headers.add("Authorization", "Bearer " + tranId);
////				headers.add("ShopId", "");
//
//				ResponseEntity<String> responseEntity = null;
//				HttpEntity<T> entity = new HttpEntity<T>((T) body, headers);
//				try
//				{
//					RestTemplate restTemplate = new RestTemplate();
//					String url = getPropValues();
//					String link = url + "/authorities?&assetCode=" + uri;
//					responseEntity = restTemplate.postForEntity(link, entity, String.class);
//					JSONObject jsonResult = new JSONObject(responseEntity);
//					String status = jsonResult.getString("body");
//					// LOG.info("responseEntity==" + status);
//					if ("0".equals(status))
//					{
//						request.getRequestDispatcher("/errorAuthorities").forward(request, response);
//						return;
//					}
//				}
//				catch (final HttpClientErrorException httpClientErrorException)
//				{
//					LOG.error(httpClientErrorException.getMessage(), httpClientErrorException);
//					request.getRequestDispatcher("/testForm").forward(request, response);
//					return;
//				} catch (HttpServerErrorException httpServerErrorException)
//				{
//					LOG.error(httpServerErrorException.getMessage(), httpServerErrorException);
//					request.getRequestDispatcher("/testForm").forward(request, response);
//					return;
//				} catch (Exception exception)
//				{
//					LOG.error(exception.getMessage(), exception);
//					request.getRequestDispatcher("/testForm").forward(request, response);
//					return;
//				}
//
//			} catch (Exception e)
//			{
//				LOG.error(e.getMessage(), e);
//				request.getRequestDispatcher("/testForm").forward(request, response);
//				return;
//			}
//			chain.doFilter(request, response);
//			return;
//		} else
//		{
//			request.getRequestDispatcher("/testForm").forward(request, response);
//			return;
//		}
//	}
//
//	public String getPropValues() throws IOException
//	{
//		InputStream inputStream = null;
//		String result = "";
//		try
//		{
//			Properties prop = new Properties();
//			String propFileName = "application.properties";
//
//			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//
//			if (inputStream != null)
//			{
//				prop.load(inputStream);
//			} else
//			{
//				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//			}
//			result = prop.getProperty("login.url");
//		} catch (Exception e)
//		{
//			LOG.error(e.getMessage(), e);
//		} finally
//		{
//			if (inputStream != null)
//				inputStream.close();
//		}
//		return result;
//	}
//
//}
