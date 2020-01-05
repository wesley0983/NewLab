package com.example.demo.service;

import static com.example.demo.constant.DemoResponseConst.ReturnCode.E0001;
import static com.example.demo.constant.DemoResponseConst.ReturnCode.E9997;

import static com.example.demo.constant.PropertiesConst.PropertiesKeyEL.RSA_PRIVATE_KEY;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.demo.dao.BookDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.UserBookDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.error.DemoException;
import com.example.demo.po.BookPO;
import com.example.demo.po.UserPO;
import com.example.demo.util.CryptoUtil;
import com.example.demo.util.DateTimeUtil;

@Service
public class UserBookService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBookService.class);
	@Autowired
	private BookDao bookDao;
	@Autowired
	private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
	@Value(RSA_PRIVATE_KEY)
	private String rsaPrivateKey;

	// 同時新增使用者和書籍
	@Transactional(rollbackFor = Exception.class)
	public UserBookDTO addUserBook(UserBookDTO userbookDTO) {
		userbookDTO.setPcode(processPcode(userbookDTO.getPcode()));
		UserPO userPO = userbookDTO.getUserPO();
		BookPO bookPO = userbookDTO.getBookPO();
		LocalDate date = DateTimeUtil.stringToDate(userbookDTO.getPublicationDate());

		bookPO.setPublicationDate(date);

		return UserBookDTO.createByUserPOBookPO(userDao.save(userPO), bookDao.save(bookPO));
	}

	// 同時變更使用者和書籍
	@Transactional(noRollbackFor = Exception.class)
	public UserBookDTO editUserByUserSeqNBookByName(UserBookDTO userbookDTO) {
//		updateUser(dao -> dao.findByUserCode(userbookDTO.getUserCode())
//				.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, "Can't found User by username.")),
//				userPO -> {
//					if (StringUtils.hasText(userbookDTO.getUserCode()))
//						userPO.setUserCode((userbookDTO.getUserCode()));
//					if (StringUtils.hasText(userbookDTO.getPcode()))
//						userPO.setPcode(processPcode(userbookDTO.getPcode()));
//					if (StringUtils.hasText(userbookDTO.getRole()))
//						userPO.setRole(userbookDTO.getRole());
//
//				}
//		);
//		updateBook(
//				dao -> dao.findByName(userbookDTO.getName())
//						.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, "Can't found Book by name.")),
//				bookPO -> {
//					if (StringUtils.hasText(userbookDTO.getName()))
//						bookPO.setName(userbookDTO.getName());
//					if (StringUtils.hasText(userbookDTO.getAuthor()))
//						bookPO.setAuthor(userbookDTO.getAuthor());
//					if (StringUtils.hasText(userbookDTO.getPublicationDate()))
//						bookPO.setPublicationDate(DateTimeUtil.stringToDate(userbookDTO.getPublicationDate()));
//				});
//		LocalDate date = DateTimeUtil.stringToDate(userbookDTO.getPublicationDate());
//		BookPO book = userbookDTO.getBookPO();
//		book.setPublicationDate(date);
//		UserPO user = userbookDTO.getUserPO();
//
//		return UserBookDTO.createByUserPOBookPO(user, book);

		return updateUserBoook(
					dao -> dao.findByUserCode(userbookDTO.getUserCode())
						.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, "Can't found User by userSeq.")),
				userPO -> {
							if (StringUtils.hasText(userbookDTO.getUserCode()))
								userPO.setUserCode(userbookDTO.getUserCode());
							if (StringUtils.hasText(userbookDTO.getPcode()))
								userPO.setPcode(userbookDTO.getPcode());
							if (StringUtils.hasText(userbookDTO.getRole()))
								userPO.setRole(userbookDTO.getRole());
		
						},
				dao -> dao.findByName(userbookDTO.getName())
				.orElseThrow(() -> DemoException.createByCodeAndExtInfo(E0001, "Can't found Book by name.")),
				bookPO -> {
					if (StringUtils.hasText(userbookDTO.getName()))
						bookPO.setName(userbookDTO.getName());
					if (StringUtils.hasText(userbookDTO.getAuthor()))
						bookPO.setAuthor(userbookDTO.getAuthor());
					if (StringUtils.hasText(userbookDTO.getPublicationDate()))
						bookPO.setPublicationDate(DateTimeUtil.stringToDate(userbookDTO.getPublicationDate()));
				});

	}

	// 變更使用者
	public UserDTO updateUser(Function<UserDao, UserPO> selector, Consumer<UserPO> setter) {
		UserPO user = selector.apply(userDao);
		setter.accept(user);

		return UserDTO.createByUserPO(user);
	}

	// 變更書籍
	public BookDTO updateBook(Function<BookDao, BookPO> selector, Consumer<BookPO> setter) {
		BookPO book = selector.apply(bookDao);
		setter.accept(book);

		return BookDTO.createByBookPO(book);
	}

	public UserBookDTO updateUserBoook(Function<UserDao, UserPO> uselector, Consumer<UserPO> usetter,
			Function<BookDao, BookPO> bselector, Consumer<BookPO> bsetter) {
		UserPO user = uselector.apply(userDao);
		usetter.accept(user);
		BookPO book = bselector.apply(bookDao);
		bsetter.accept(book);

		return UserBookDTO.createByUserPOBookPO(user, book);
	}
	   private String processPcode(String inputPcode) {
	        String decryptedPcode;
	        try {
	            decryptedPcode = CryptoUtil.rsaDecryptByPrivateKey(inputPcode, rsaPrivateKey);
	        } catch (Exception e) {
	            LOGGER.error("Decrypted failed by value: ", e);
	            throw DemoException.createByCode(E9997);
	        }
	        return passwordEncoder.encode(decryptedPcode);
	    }
	  
	

	
}
