/**
 * Created by saman on 4/29/17.
 */
angular.module('myApp')
    .config(['$translateProvider', function ($translateProvider) {
        $translateProvider.translations('en', {
            'title': 'Utadoc',
            'dateformat': 'MM/DD/YYYY, HH:mm',
            'header': {
                'home': 'Home',
                'validate': 'Validate',
                'signup': 'Sign up',
                'login': 'Login',
                'logout': 'Log out',
                'profile': 'Profile',
                'credit': 'Credit',
                'utabit': 'UTB',
                'menu': {
                    'buycredit': 'Buy Credit',
                    'transactions': 'Transactions'
                }
            },
            'home': {
                'a': {
                    'fingerprint': 'How Utadoc helps to make your document fingerprints everlasting?',
                    'patent': 'Patents',
                    'industrial': 'Industrial plans',
                    'business': 'Business plans',
                    'contracts': 'Contracts',
                    'documents': 'Documents',
                    'cheque': 'Paychecks',
                    'art': 'Artistic and Creative works',
                    'scientific': 'Research proposals and Papers',
                    'ideas': 'Ideas'
                },
                'b': {
                    'a': {
                        'title': 'Receive certificates without exposing your files',
                        'body': 'No upload required and no third party involved in issuing your unique and provable certificates'
                    },
                    'b': {
                        'title': 'Share your works without fear of being stolen',
                        'body': 'Authentication and verification of your certificates is always accessible which allows you to easily prove your ownership at anytime'
                    },
                    'c': {
                        'title': 'Manipulation of certificates is impossible',
                        'body': 'Your certificates will be stored in Utabit blockchain which provides maximum immutability and security'
                    },
                    'd': {
                        'title': 'Detect changes in your documents',
                        'body': 'Worried about unwanted changes in your documents? Just compare stored certificates to make sure about integrity'
                    }
                },
                'c': {
                    'a': {
                        'title': 'Safe',
                        'body': 'Your certificates will be hosted by a network of powerful servers spread across the world'
                    },
                    'b': {
                        'title': 'Protected',
                        'body': 'No one including Utadoc can access your data by having your certificate since it only contains the file fingerprint'
                    },
                    'c': {
                        'title': 'Transparent',
                        'body': 'Utabit blockchain is a decentralized platform which allows universal access with the highest transparency level'
                    },
                    'd': {
                        'title': 'Cheap',
                        'body': 'Registration and issuance fee for certificates is very low regarding its intellectual and monetary values'
                    },
                    'e': {
                        'title': 'Immutable',
                        'body': 'Once your certificates registered and stored on Utabit blockchain, No one can never, EVER edit or delete them'
                    },
                    'f': {
                        'title': 'Global',
                        'body': 'Anyone from anywhere can easily check the authenticity of your certificates'
                    },
                    'g': {
                        'title': 'Credible',
                        'body': 'Utabit blockchain guarantees the creation and ownership of your files with timestamping'
                    },
                    'h': {
                        'title': 'Fast',
                        'body': 'It only takes 15 minutes to receive your certificate'
                    },
                    'i': {
                        'title': 'Accesible',
                        'body': 'Without any necessity or requirements connect to Utadoc using a computer or a smartphone and request a certificate for your files'
                    }
                },
                'services' : {
                    'title': 'Utadoc do your work'
                },
	            'adv' : {
                	'title': 'Why Utadoc?'
	            }
            },
            'footer': {
                'using_utadoc': 'Using Utadoc',
                'more_info': 'More about us',
                'download_app': 'Download App',
                'desc': 'The first Document Authentication and Timestamping System (Utadoc) for different kinds of digital contents such as documents, plans, ideas as well as business, scientific and artistic works in the immutable platform of blockchain',
                'help': 'Help',
                'faq': 'FAQ',
                'terms': 'Terms and conditions',
                'privacy': 'Privacy policy',
                'whitepaper': 'White Paper',
                'aboutus': 'About us',
                'enterprise': 'Enterprise solutions',
                'contact': 'Contact us',
                'job': 'Job Opportunities'
            },
            'upload': {
                'totalyoffline': 'You do not need to upload your files, processing of fingerprints is completely offline',
                'choose': 'Choose your file',
                'drag': 'or drag it here',
                'wait': 'Please wait...',
                'desc': 'When your files are registered in Utadoc, You can prove your ownership anytime anywhere'
            },
            'sidePages': {
                'about': {
                    'title': 'About us',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'whitePaper': {
                    'title': 'White Paper',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'privacy': {
                    'title': 'Privacy Policy',
                    'body': {
                        'line1': ' Using our services means that you agree to follow all the required rules.',
                        'line2': ' Don’t misuse our Services. For example, don’t interfere with our Services or try to access them using a method other than the interface and the instructions that we provide. You may use our Services only as permitted by law, including applicable export and re-export control laws and regulations. We may suspend or stop providing our Services to you if you do not comply with our terms or policies or if we are investigating suspected misconduct.',
                        'line3': ' Using our Services does not give you ownership of any intellectual property rights in our Services or the content you access. You may not use content from our Services unless you obtain permission from its owner or are otherwise permitted by law.',
                        'line4': ' These terms do not grant you the right to use any branding or logos used in our Services.',
                        'line5': ' Don’t remove, obscure, or alter any legal notices displayed in or along with our Services.',
                        'line6': ' You may need a Google Account in order to use some of our Services. To protect your Google Account, keep your password confidential. You are responsible for the activity that happens on or through your Google Account. Try not to reuse your Google Account password on third-party applications.',
                        'line7': ' Google’s privacy policies explain how we treat your personal data and protect your privacy when you use our Services.',
                        'line8': ' By using our Services, you agree that Google can use such data in accordance with our privacy policies.',
                        'line9': ' Some of our Services allow you to upload, submit, store, send or receive content. You retain ownership of any intellectual property rights that you hold in that content. In short, what belongs to you stays yours.',
                        'line10': ' We are constantly changing and improving our Services. We may add or remove functionalities or features, and we may suspend or stop a Service altogether.',
                        'line11': ' We believe that you own your data and preserving your access to such data is important. If we discontinue a Service, where reasonably possible, we will give you reasonable advance notice and a chance to get information out of that Service.',
                        'line12': ' We may modify these terms or any additional terms that apply to a Service to, for example, reflect changes to the law or changes to our Services. You should look at the terms regularly. We’ll post notice of modifications to these terms on this page. We’ll post notice of modified additional terms in the applicable Service. Changes will not apply retroactively and will become effective no sooner than fourteen days after they are posted. However, changes addressing new functions for a Service or changes made for legal reasons will be effective immediately. If you do not agree to the modified terms for a Service, you should discontinue your use of that Service.',
                        'line13': ' If there is a conflict between these terms and the additional terms, the additional terms will control for that conflict.',
                    }
                },
                'terms': {
                    'title': 'Terms and Conditions',
                    'body': {
                        'line1': ' Using our services means that you agree to follow all the required rules.',
                        'line2': ' Don’t misuse our Services. For example, don’t interfere with our Services or try to access them using a method other than the interface and the instructions that we provide. You may use our Services only as permitted by law, including applicable export and re-export control laws and regulations. We may suspend or stop providing our Services to you if you do not comply with our terms or policies or if we are investigating suspected misconduct.',
                        'line3': ' Using our Services does not give you ownership of any intellectual property rights in our Services or the content you access. You may not use content from our Services unless you obtain permission from its owner or are otherwise permitted by law.',
                        'line4': ' These terms do not grant you the right to use any branding or logos used in our Services.',
                        'line5': ' Don’t remove, obscure, or alter any legal notices displayed in or along with our Services.',
                        'line6': ' You may need a Google Account in order to use some of our Services. To protect your Google Account, keep your password confidential. You are responsible for the activity that happens on or through your Google Account. Try not to reuse your Google Account password on third-party applications.',
                        'line7': ' Google’s privacy policies explain how we treat your personal data and protect your privacy when you use our Services.',
                        'line8': ' By using our Services, you agree that Google can use such data in accordance with our privacy policies.',
                        'line9': ' Some of our Services allow you to upload, submit, store, send or receive content. You retain ownership of any intellectual property rights that you hold in that content. In short, what belongs to you stays yours.',
                        'line10': ' We are constantly changing and improving our Services. We may add or remove functionalities or features, and we may suspend or stop a Service altogether.',
                        'line11': ' We believe that you own your data and preserving your access to such data is important. If we discontinue a Service, where reasonably possible, we will give you reasonable advance notice and a chance to get information out of that Service.',
                        'line12': ' We may modify these terms or any additional terms that apply to a Service to, for example, reflect changes to the law or changes to our Services. You should look at the terms regularly. We’ll post notice of modifications to these terms on this page. We’ll post notice of modified additional terms in the applicable Service. Changes will not apply retroactively and will become effective no sooner than fourteen days after they are posted. However, changes addressing new functions for a Service or changes made for legal reasons will be effective immediately. If you do not agree to the modified terms for a Service, you should discontinue your use of that Service.',
                        'line13': ' If there is a conflict between these terms and the additional terms, the additional terms will control for that conflict.',
                    }
                },
                'job': {
                    'title': 'Job opportunities',
                    'body1': 'Ready to build the future?',
                    'body2': ' In Utadoc, we are programming the future by developing unprecedented and revolutionary solutions for organizations. If you are a genius coder whose capabilities are not discovered yet or an expert researcher in the field of technology who thinks beyond ordinary technologies, we have challenging and attractive jobs for you.',

                    'body3': 'Send an email to prove your skills and claim your job!'
                },
                'help': {
                    'title': 'Help',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'enterprise': {
                    'title': 'Enterprise Solutions',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'contact': {
                    'addressTitle': 'Address:',
                    'address': ' Unit 7-No 210- Dastgerdi (Zafar) St – Tehran - Iran',
                    'phoneTitle': 'Phone:'
                },
                'invite': {
                    'title': 'Invite Friends',
                    'sendButton': 'Send Email'
                },
                'faq': {
                    'title': 'FAQ',
                    'item1': {
                        'question': 'Can I request certificates for all kinds of files extensions?',
                        'answer': 'Yes. You can request certificates for every file extensions including text, music, movie etc.'
                    },
                    'item2': {
                        'question': 'Does Utadoc access my documents?',
                        'answer': 'No. Although you can upload a copy of your file in our servers, it is not mandatory. The fingerprint processing is also completely offline.'
                    },
                    'item3': {
                        'question': 'Is it possible for a third party to access the content of my document with my fingerprint?',
                        'answer': 'No. in Utadoc we use the most reliable cryptographic solutions that provide an impenetrable file fingerprint.'
                    },
                    'item4': {
                        'question': 'How can I make sure Utadoc is always up and running?',
                        'answer': 'Since your certificates stored in Utabit blockchain, in case of shutting down Utadoc you can still use Utabit blockchain explorers to access yours.'
                    },
                    'item5': {
                        'question': 'How long does it take to verify a certificate?',
                        'answer': 'Once you paid the registration fee Utadoc begins generating your unique file fingerprint which takes almost 10 minutes. However, in some instances it may require up to 60 minutes.'
                    },
                    'item6': {
                        'question': 'How can I use my certificates?',
                        'answer': 'Your certificates work like authenticators that show your ownership rights over your files. Comparing your certificate with file fingerprint can be done in Utabit blockchain explorers. When both matched, you can prove that you created the file in a specific time and its content has not changed yet.'
                    },
                    'item7': {
                        'question': 'Will my certificate remain the same if I change the content of my file?',
                        'answer': 'No. even the slightest changes in your file content would completely change its fingerprint. Thus, save your files in a secure place like Utadoc servers and keep backup copies at your disposal.'
                    },
                    'item8': {
                        'question': 'How can I use my Utadoc certificate in legal conflicts?',
                        'answer': ' provides a valid and reliable certificate to prove your ownership. It also clarifies that whether the content of a document has manipulated or not. However, its acceptance by law has been a controversial issue. We strongly recommend that you consult with your lawyer before using your certificates as evidences.'
                    }
                }
            },
            'payment': {
                'buyCredit': 'Buy Credit',
                'amount': 'Amount',
                'amountUtabit': 'Amount(Ubit)',
                'amountRial': 'Amount(IRRial)',
                'paymentButton': 'Payment',
                'paymentResult': 'Payment Result',
                'paymentAddress': 'Payment Address',
                'paymentAddressDesc': 'Please scan the QR Code.',
                'invoice': 'transaction tracking number',
                'yourCredit': 'Your Credit',
                400: 'Requested Utabit amount is less than min.',
                'resultMsg': {
                    'timeLeft': 'Estimated time for verifying your payment: between ',
                    'whenDone': 'After payment confirmed, your account credit will be charged.'
                }
            },
            'profile': {
                'title': 'Profile',
                'name': 'Name',
                'family': 'Family',
                'currentPass': 'Current Password',
                'changePass': 'Change Password',
                'edit': 'Edit',
                200: 'Profile successfully updated.',
                204: 'Your identity information is not valid.',
                400: 'Bad request.',
                403: 'Your current password is incorrect.',
                500: 'Updating your profile interrupted by an error.',

            },
            'passwordRecover': {
                'forgotPassword': 'Forgot Password',
                'recoverPassword': 'Recover Password',

            },
            'recover': {
                'expired': 'Your request is expired.',
                'invalidData': 'Data for password recovery is invalid.',
                'changeException': 'We cannot recover the password.'
            },
            'signin': {
                'signup': 'Create a free account',
                'signin': 'I have an account',
                'forgotPassword': 'Forgot your password?',
                200: 'Successfully logged in.',
                204: 'Incorrect username or password.',
                401: 'Incorrect username or password.',
                400: 'Bad request.'
            },
            'signup': {
                'fieldGroup':{
                    'company': 'Company',
	                'account': 'Account',
	                'creativeDirector': 'Creative Director'
                },
	            'companyName': 'Company Name',
				'companyActivity':'Company Activity',
	            'postalAddress':'Postal Address',
	            'city':'City',
	            'postCode':'Post Code',
	            'country':'Country',
	            'companyPhone':'Company Phone',
	            'companyWebsite':'Company Website',
	            'cdName':'',
	            '':'',

	            200: 'Successfully registered.',
                204: 'Required field is empty.',
                226: 'The email address you entered is already registered.Please enter another email address.',
                400: 'Bad request.',
                412: 'Your password and confirmation password do not match.'
            },
            'transaction': {
                'showDetail': 'More Details',
                'invalidDoc': 'Invalid Document!',
                'createTx': 'Create Transaction',
                'fileUploaded1': 'Upload Files on ',
                'fileUploaded2': ' servers.',
                'detail': {
                    'title': 'Transaction Details',
                    204: 'No transaction matches this number.'
                },
                'download': 'Download Document',
                'relativeTime': {
                    'day': ' day,',
                    'hour': ' hour,',
                    'minute': ' min,',
                    'second': ' sec',
                    'ago': ' ago',

                },
                'status': {
                    'waiting': 'Pending',
                    'confirmed': 'Confirmed'
                },
	            'paymentStatus': {
		            'Paid': 'Payment Done',
		            'Pending': 'Pending for Payment'
	            },
                'remainingTime': {
                    'leftMsg': 'Estimated time for verifying transaction: between ',
                    'to': ' to ',
                    'minute': ' min'
                },
                'list': {
                    'title': 'Transaction List',
                    400: 'Chosen time period is incorrect.'
                },
                'showAll': 'Show All',
                'fromDate': 'From date',
                'toDate': 'To date',
                'create': {
	                1001:'Please select one of the document types.',
                    500: 'Uploading file or registering transaction interrupted by an error.Please try again.',
                    400: 'Bad request.',
                    208: 'This file is already registered. Registering again is impossible.',
                    507: 'Your account balance is not enough.',
                    509: 'Server is currently overloaded. Please try again'
                },
                'validate': {
                    404: 'This document is not already registered.'
                }
            },
            'general': {
                'words': {
                    'signin': 'Signin',
                    'signup': 'Signup',
                    'email': 'Email',
                    'password': 'Password',
                    'confirmPassword': 'Confirm Password',
                    'search': 'Search',
                    'okButton': 'OK',
                    'close': 'Close',
                    'submit': 'Submit',
                    'desc': 'Description',
                    'hash': 'Hash',
                    'id': 'ID',
                    'status': 'Status',
                    'date': 'Date',
                    'detail': 'Details',
                    'file': 'File',
                    'clear': 'Clear',
	                'name': 'First Name',
	                'lastName': 'Last Name',
	                'job': 'Job Title',
	                'phone': 'Phone No.',
                    'language':'Language'
                },
                'input': {
                    'required': 'This field is required.',
                    'validEmail': 'Please enter a valid email address.',
                    'minlength': {
                        '6': 'At least enter 6 characters.'
                    }
                },
                'error': {
                    500: 'Error! Please try again.'
                }
            },
            'auth': {
                'google': {
                    'permissionDenied': '"Utadoc" cannot access Google.',
                    'inviteError': 'We cannot send you the invitation.'
                }
            }
        });

        $translateProvider.translations('ru', {
            'title': 'Utadoc',
            'dateformat': 'MM/DD/YYYY, HH:mm',
            'header': {
                'home': 'дом',
                'validate': 'утверждение',
                'signup': 'регистрация',
                'login': 'вход',
                'logout': 'Выйти',
                'profile': 'Профиль',
                'credit': 'кредит',
                'utabit': 'UTB',
                'menu': {
                    'buycredit': 'Купить кредит',
                    'transactions': 'операции'
                }
            },
            'home': {
                'a': {
                    'fingerprint': 'Как ваш отпечаток будет вечным?',
                    'patent': 'декларация патентa',
                    'industrial': 'промышленные планы',
                    'business': 'Бизнес планы',
                    'contracts': 'Контракты',
                    'documents': 'документы',
                    'cheque': 'Чек',
                    'art': 'Литературные и художественные произведения',
                    'scientific': 'Научные статьи',
                    'ideas': 'Идеи'
                },
                'b': {
                    'a': {
                        'title': 'Получать сертификаты без раскрытия файлов',
                        'body': 'Вам не нужно доверять кому-либо, чтобы подтвердить вашу информацию. Вы сможете создавать уникальные и проверенные сертификаты для себя без необходимости загрузки файлов'
                    },
                    'b': {
                        'title': 'Дайте ваши планы другим без беспокойства',
                        'body': 'Доказательство действительности и подлинности вашего сертификата всегда практично. Итак, в любое время вы можете легко показать, что этот план принадлежит вам'
                    },
                    'c': {
                        'title': 'Невозможно манипулировать ваш сертификат',
                        'body': 'Ваш сертификат хранится на блокчейнe Ютабит. Никакая зарегистрированная информация на Ютабитe  не может быть изменена или удалена'
                    },
                    'd': {
                        'title': 'Легко идентифицировать манипуляцию документов',
                        'body': 'Беспокоитесь о манипулировании документами и информацией? Удостоверьтесь, что они в безопасности, сохраняя их и сопоставляя их при необходимости.'
                    }
                },
                'c': {
                    'a': {
                        'title': 'Безопасный',
                        'body': 'Ваш сертификат находится на мощной сети серверов по всему миру.'
                    },
                    'b': {
                        'title': 'Нет необходимости раскрывать информацию',
                        'body': 'Ваш сертификат содержит только отпечаток вашего файла. В любом случае никто (даже система) не может получить доступ к вашим данным.'
                    },
                    'c': {
                        'title': 'прозрачность',
                        'body': 'блокчейн Ютабитa полностью распределена, и любой может получить к ней доступ в любой точке мира.'
                    },
                    'd': {
                        'title': 'дешево',
                        'body': 'Стоимость регистрации сертификата для важного плана или контракта очень низка в отношении его материальной и духовной ценности.'
                    },
                    'e': {
                        'title': 'Неманипулируемой',
                        'body': 'Никто не может манипулировать и удалять ваш сертификат. То, что было записано, никогда не будет стерто.'
                    },
                    'f': {
                        'title': 'Может быть просмотрен всем',
                        'body': 'Любой человек в любой точке мира может легко проверить ваши учетные данные.'
                    },
	                'body': 'структура и технология использовано  в блокчейне, гарантируют, что файл был установлен и доступен  в определенное время.',
	                'g': {
	                    'title': 'можно ссылаться'
                    },
                    'h': {
                        'title': 'быстро',
                        'body': 'Создание сертификата займет около 15 минут.'
                    },
                    'i': {
                        'title': 'доступный',
                        'body': 'Вы независимы. Вы можете создать собственный сертификат с помощью компьютера или с помощью мобильного телефона.'
                    }
                },
	            'services' : {
		            'title': 'Utadoc do your work'
	            },
	            'adv' : {
		            'title': 'Why Utadoc?'
	            }
            },
            'footer': {
	            'using_utadoc': 'Using Utadoc',
	            'more_info': 'More about us',
	            'download_app': 'Download App',
                'desc': 'Первая система регистрационных сертификатов, незаменимая для разнообразного цифрового контента, документов, дизайнов и идей, художественных работ и научных и коммерческих работ на основе блокчейна',
                'help': 'Помогите',
                'faq': 'Часто задаваемые вопросы',
                'terms': 'Условия и положения',
                'privacy': 'Конфиденциальность',
                'whitepaper': 'белая бумага',
                'aboutus': 'о нас',
                'enterprise': 'Решение для предприятий',
                'contact': 'свяжитесь с нами',
                'job': 'Карьерные возможности'
            },
            'upload': {
                'totalyoffline': 'Расчет отпечатка пальца полностью в оффлайне, и ваш файл не будет загружен нигде',
                'choose': 'Выберите файл',
                'drag': 'Или перетащите сюда',
                'wait': 'пожалуйста, подождите...',
                'desc': 'Как только ваш файл зарегистрирован в этой системе, вы можете показать навсегда и безотзывно, что этот документ был вашим в настоящее время.'
            },
            'sidePages': {
                'about': {
                    'title': 'о нас',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'whitePaper': {
                    'title': 'белая бумага',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'privacy': {
                    'title': 'политика конфиденциальности',
                    'body': {
                        'line1': ' Вы должны следовать всем политикам, предоставляемым сервисами.',
                        'line2': ' Не злоупотребляйте нашими услугами. Например, не мешайте нашим службам или пытайтесь получить к ним доступ, используя метод, отличный от интерфейса и инструкций, предоставленных нами. Вы можете воспользоваться нашими услугами только в соответствии с законом, в том числе действующих законов и правил для экспорта и импортного контроля. В случае несоблюдения наших условий или правил, или в случае подозрения в любой неисправности, мы можем приостановить или прекратить предоставление Услуг.',
                        'line3': ' Использование наших Услуг не приводит к тому, что вы обладаете правами на интеллектуальную собственность в наших Сервисах или в том материале, к которому у вас есть доступ. Вы не можете использовать содержание наших услуг, если владелец этих услуг не разрешает вам или иным образом не разрешено законом.',
                        'line4': ' Эти условия не дают вам права использовать бренды или логотипы, используемые в этих сервисах.',
                        'line5': ' Не удаляйте, не изменяйте или не скрывайте юридические уведомления, отображаемые в наших Сервисах.',
                        'line6': ' Возможно, вам понадобится использовать учетную запись Google для использования некоторых наших услуг. Не раскрывайте свой пароль для защиты своего аккаунта Google. Вы несете ответственность за события, происходящие в вашей учетной записи Google или через нее. Не пытайтесь повторно использовать свой пароль учетной записи Google в сторонних приложениях.',
                        'line7': ' Политика конфиденциальности Google описывает, как мы используем ваши личные данные и защищаем вашу конфиденциальность при использовании наших услуг.',
                        'line8': ' Используя наши услуги, вы соглашаетесь с тем, что Google может использовать эти данные в соответствии с нашей Политикой конфиденциальности.',
                        'line9': ' Некоторые из наших услуг позволяют вам загружать, , представлять, хранить, отправлять или получать контент. Вы сохраните права собственности на любые права интеллектуальной собственности, которые вы держите в отношении контента. Короче говоря, то, что принадлежит вам, по-прежнему будет принадлежать вам.',
                        'line10': ' Мы постоянно меняем и совершенствуем наши услуги. Мы можем добавлять или удалять функции, а также приостанавливать или полностью останавливать одну из наших служб.',
                        'line11': ' Мы считаем, что ваши данные ваши, и ваш доступ к таким данным очень важен. Если мы отключим услугу, где это возможно, мы сообщим вам так, как это уже делается, и предоставим вам возможность снять свою информацию с этой службы.',
                        'line12': ' Мы можем изменить эти дополнительные условия, которые относятся к одной из наших услуг, чтобы отражать, например, изменения в законе или изменения наших услуг. Вы должны часто проверять условия. Мы сделаем объявления о поправках к этим правилам на этой странице. Мы опубликуем уведомление об измененных дополнительных условиях применимых услуг. Изменения не будут выполнены раньше, чем 14 дней после их публикации. Тем не менее, изменения в новых функциях для одной из услуг или изменений, созданных по юридическим причинам, будут немедленно соблюдены. Если вы не согласны с измененными условиями для одной из служб, вы должны прекратить использовать эту услугу.',
                        'line13': ' Если есть конфликт между этими условиями и дополнительными условиями, дополнительные условия будут приоритетными для преодоления помех.',
                    }
                },
                'terms': {
                    'title': 'Правила и условия',
                    'body': {
                        'line1': ' Вы должны следовать всем политикам, предоставляемым сервисами.',
                        'line2': ' Не злоупотребляйте нашими услугами. Например, не мешайте нашим службам или пытайтесь получить к ним доступ, используя метод, отличный от интерфейса и инструкций, предоставленных нами. Вы можете воспользоваться нашими услугами только в соответствии с законом, в том числе действующих законов и правил для экспорта и импортного контроля. В случае несоблюдения наших условий или правил, или в случае подозрения в любой неисправности, мы можем приостановить или прекратить предоставление Услуг.',
                        'line3': ' Использование наших Услуг не приводит к тому, что вы обладаете правами на интеллектуальную собственность в наших Сервисах или в том материале, к которому у вас есть доступ. Вы не можете использовать содержание наших услуг, если владелец этих услуг не разрешает вам или иным образом не разрешено законом.',
                        'line4': ' Эти условия не дают вам права использовать бренды или логотипы, используемые в этих сервисах.',
                        'line5': ' Не удаляйте, не изменяйте или не скрывайте юридические уведомления, отображаемые в наших Сервисах.',
                        'line6': ' Возможно, вам понадобится использовать учетную запись Google для использования некоторых наших услуг. Не раскрывайте свой пароль для защиты своего аккаунта Google. Вы несете ответственность за события, происходящие в вашей учетной записи Google или через нее. Не пытайтесь повторно использовать свой пароль учетной записи Google в сторонних приложениях.',
                        'line7': ' Политика конфиденциальности Google описывает, как мы используем ваши личные данные и защищаем вашу конфиденциальность при использовании наших услуг.',
                        'line8': ' Используя наши услуги, вы соглашаетесь с тем, что Google может использовать эти данные в соответствии с нашей Политикой конфиденциальности.',
                        'line9': ' Некоторые из наших услуг позволяют вам загружать, , представлять, хранить, отправлять или получать контент. Вы сохраните права собственности на любые права интеллектуальной собственности, которые вы держите в отношении контента. Короче говоря, то, что принадлежит вам, по-прежнему будет принадлежать вам.',
                        'line10': ' Мы постоянно меняем и совершенствуем наши услуги. Мы можем добавлять или удалять функции, а также приостанавливать или полностью останавливать одну из наших служб.',
                        'line11': ' Мы считаем, что ваши данные ваши, и ваш доступ к таким данным очень важен. Если мы отключим услугу, где это возможно, мы сообщим вам так, как это уже делается, и предоставим вам возможность снять свою информацию с этой службы.',
                        'line12': ' Мы можем изменить эти дополнительные условия, которые относятся к одной из наших услуг, чтобы отражать, например, изменения в законе или изменения наших услуг. Вы должны часто проверять условия. Мы сделаем объявления о поправках к этим правилам на этой странице. Мы опубликуем уведомление об измененных дополнительных условиях применимых услуг. Изменения не будут выполнены раньше, чем 14 дней после их публикации. Тем не менее, изменения в новых функциях для одной из услуг или изменений, созданных по юридическим причинам, будут немедленно соблюдены. Если вы не согласны с измененными условиями для одной из служб, вы должны прекратить использовать эту услугу.',
                        'line13': ' Если есть конфликт между этими условиями и дополнительными условиями, дополнительные условия будут приоритетными для преодоления помех.',
                    }
                },
                'job': {
                    'title': 'Карьерные возможности',
                    'body1': 'Готов выяснить будущее?',
                    'body2': ' Ариатак рисует и программирует будущее. Мы предоставляем решения для организаций, которые раньше не были возможны. Если вы гениальный разработчик, который не написал код в своем собственном качестве, и если вы технологический исследователь, чья способность намного больше, чем просто проверка обычных инструментов и технологий, мы здесь найдем что-то интересное и интересное для  вас',

                    'body3': 'Отправьте текст по электронной почте ниже, чтобы указать, что вы такой человек.'
                },
                'help': {
                    'title': 'Помогите',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'enterprise': {
                    'title': 'Решение для предприятий',
                    'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                },
                'contact': {
                    'addressTitle': 'адрес:',
                    'address': ' Unit 7-No 210- Dastgerdi (Zafar) St – Tehran - Iran',
                    'phoneTitle': 'телефон:'
                },
                'invite': {
                    'title': 'Пригласить друзей',
                    'sendButton': 'Отправить электронное письмо'
                },
                'faq': {
                    'title': 'Часто задаваемые вопросы',
                    'item1': {
                        'question': 'Только для текстовых файлов могут быть созданы сертификаты?',
                        'answer': 'Нет. Вы можете создавать сертификаты для любого типа файлов с любым расширением, включая текст, музыку, видео и многое другое.'
                    },
                    'item2': {
                        'question': 'Имеет ли Utadoc доступ к моим документам?',
                        'answer': 'Нет. Отслеживание отпечатков пальцев выполняется в автономном режиме, и ваши данные не будут загружены на наш сервер.'
                    },
                    'item3': {
                        'question': 'Может ли кто-нибудь получить доступ к моему контенту, используя мой отпечаток?',
                        'answer': 'Это невозможно. Создание отпечатка пальца в Utadoc выполняется с использованием самых широко используемых в мире решений для шифрования, и ни один не может извлекать информацию из отпечатка пальца.'
                    },
                    'item4': {
                        'question': 'Как я могу быть уверен, что Utadoc всегда будет?',
                        'answer': 'Вам не нужно быть уверенным. Ваши одобрения хранятся нa блокчейнe ютабита, и поэтому, даже если Utadoc не существует, вы можете в любое время проверить свои разрешения на сайтах, которые ищут блокчейн ютабита.'
                    },
                    'item5': {
                        'question': 'Как долго проходит сертификация?',
                        'answer': 'После оплаты сертификата процесс регистрации начинается с банка (Что обычно занимает несколько минут). Это обычно занимает 10 минут, но в особых случаях это может занять до часа. Utadoc пытается обеспечить условия для самого быстрого периода утверждения сертификации, учитывая статус блокчейнa ютабита.'
                    },
                    'item6': {
                        'question': 'Как я могу использовать свой сертификат документа в будущем?',
                        'answer': 'Чтобы продемонстрировать правильность вашего сертификата, вы должны предоставить документ, для которого вы получили сертификат через веб-сайт Utadoc или другими способами производить отпечатки пальцев и соответствуйтe отпечаткам пальцев, которые отправляются на ваш адрес электронной почты и в блокчейнe ютабита. Чтобы соответствовать отпечатку пальца, просто перейдите на сайты исследователи блокчейнa ютабита и введя номер транзакции, просмотрите информацию о транзакции, в которой хранится ваш сертификат. Согласование этого сертификата с отпечатком файла, который у вас есть, указывает, что вы были владельцем этого файла в то время, и его содержимое пока не изменилось.'
                    },
                    'item7': {
                        'question': 'Могу ли я использовать этот сертификат, если я изменю файл, созданный для этого сертификата?',
                        'answer': 'Нет. Создание малейшего изменения в файле полностью изменит его отпечаток. Поэтому обязательно сохраняйте документы, которые вы сертифицировали для них, в безопасном месте и имейте резервную копию. Вы также можете иметь копию своего файла на зарегистрированных серверах, если хотите.'
                    },
                    'item8': {
                        'question': 'Как подтверждение Utadoc помогает мне юридически?',
                        'answer': 'Utadoc Обеспечивает четкое доказательство владения файлом. Это также подтверждает, что содержимое документа не обрабатывалось во время закрытия. Но степень, в которой эта цитата является приемлемой в судебных судах может быть спорной. Если у вас есть вопросы в этой области, обязательно проконсультируйтесь с вашим адвокатом.'
                    }
                }
            },
            'payment': {
                'buyCredit': 'Купить кредит',
                'amount': 'Количество',
                'amountUtabit': 'Количество(Ubit)',
                'amountRial': 'Количество(IRRial)',
                'paymentButton': 'Оплата',
                'paymentResult': 'Результат платежа',
                'paymentAddress': 'Адрес оплаты',
                'paymentAddressDesc': 'Сканируйте QR-код.',
                'invoice': 'Номер отслеживания транзакции',
                'yourCredit': 'Ваш кредит',
                400: 'Требуемое количество Utabit меньше, чем мин.',
                'resultMsg': {
                    'timeLeft': 'Расчетное время для проверки платежа: между ',
                    'whenDone': 'После подтверждения оплаты, ваш счет будет списана.'
                }
            },
            'profile': {
                'title': 'Профиль',
                'name': 'имя',
                'family': 'Фамилия',
                'currentPass': 'Текущий пароль',
                'changePass': 'Изменить пароль',
                'edit': 'редактировать',
                200: 'Профиль успешно обновлен.',
                204: 'Ваша личная информация недействительна.',
                400: 'Плохой запрос.',
                403: 'Ваш текущий пароль неверен.',
                500: 'Обновление вашего профиля, прерванного ошибкой.',


            },
            'passwordRecover': {
                'forgotPassword': 'Забыли пароль',
                'recoverPassword': 'Восстановить пароль',

            },
            'recover': {
                'expired': 'Срок действия вашего запроса истек.',
                'invalidData': 'Данные для восстановления пароля недействительны.',
                'changeException': 'Мы не можем восстановить пароль.'
            },
            'signin': {
                'signup': 'Создать бесплатный аккаунт',
                'signin': 'У меня есть аккаунт',
                'forgotPassword': 'Забыли пароль?',
                200: 'Успешно вошел в систему.',
                204: 'Неверное имя пользователя или пароль.',
                401: 'Неверное имя пользователя или пароль.',
                400: 'Плохой запрос.'
            },
            'signup': {
                200: 'Успешно зарегистрирован.',
                204: 'Обязательное поле пуст.',
                226: 'Введенный вами адрес электронной почты уже зарегистрирован. Введите другой адрес электронной почты.',
                400: 'Плохой запрос.',
                412: 'Ваш пароль и пароль подтверждения не совпадают.'
            },
            'transaction': {
                'showDetail': 'Подробнее',
                'invalidDoc': 'Неверный документ!',
                'createTx': 'Создать транзакцию',
                'fileUploaded1': 'Загрузка файлов на ',
                'fileUploaded2': ' серверы.',
                'detail': {
                    'title': 'Детали транзакции',
                    204: 'Никакая транзакция не соответствует этому числу.'
                },
                'download': 'Загрузить документ',
                'relativeTime': {
                    'day': ' день,',
                    'hour': ' час,',
                    'minute': ' минут,',
                    'second': ' второй',
                    'ago': ' тому назад',

                },
                'status': {
                    'waiting': 'в ожидании',
                    'confirmed': 'подтвердил'
                },
                'remainingTime': {
                    'leftMsg': 'Расчетное время для проверки транзакции: между ',
                    'to': ' в ',
                    'minute': ' минут'
                },
                'list': {
                    'title': 'Список транзакций',
                    400: 'Выбранный период времени неверен.'
                },
                'showAll': 'Показать все',
                'fromDate': 'С даты',
                'toDate': 'Встретиться',
                'create': {
	                1001:'Please select one of the document types.',
	                500: 'Загрузка файла или регистрация транзакции прерывается ошибкой. Повторите попытку.',
                    400: 'Плохой запрос.',
                    208: 'Этот файл уже зарегистрирован. Регистрация снова невозможна.',
                    507: 'Недостаточно вашего баланса.',
                    509: 'Сервер в настоящее время перегружен. Пожалуйста, попробуйте еще раз.'
                },
                'validate': {
                    404: 'Этот документ еще не зарегистрирован.'
                }
            },
            'general': {
                'words': {
                    'signin': 'вход',
                    'signup': 'регистрация',
                    'email': 'Эл. адрес',
                    'password': 'пароль',
                    'confirmPassword': 'Подтвердите Пароль',
                    'search': 'поиск',
                    'okButton': 'ОК',
                    'close': 'Закрыть',
                    'submit': 'Отправить',
                    'desc': 'описание',
                    'hash': 'Hash',
                    'id': 'ID',
                    'status': 'положение дел',
                    'date': 'Дата',
                    'detail': 'Детали',
                    'file': 'файл',
                    'clear': 'Чисто'
                },
                'input': {
                    'required': 'Это поле обязательно к заполнению.',
                    'validEmail': 'Пожалуйста, введите действительный адрес электронной почты.',
                    'minlength': {
                        '6': 'По крайней мере, введите 6 символов.'
                    }
                },
                'error': {
                    500: 'Ошибка! Пожалуйста, попробуйте еще раз.'
                }
            },
            'auth': {
                'google': {
                    'permissionDenied': '"Utadoc" не может получить доступ к Google.',
                    'inviteError': 'Мы не можем отправить вам приглашение.'
                }
            }
        });

        $translateProvider.translations('fa', {
                'title': 'Utadoc',
                'dateformat': 'MM/DD/YYYY, HH:mm',
                'header': {
                    'home': 'Home',
                    'validate': 'Validate',
                    'signup': 'Sign up',
                    'login': 'Login',
                    'logout': 'Log out',
                    'profile': 'Profile',
                    'credit': 'Credit',
                    'utabit': 'UTB',
                    'menu': {
                        'buycredit': 'Buy Credit',
                        'transactions': 'Transactions'
                    }
                },
                'home': {
                    'a': {
                        'fingerprint': 'How Utadoc helps to make your document fingerprints everlasting?',
                        'patent': 'Patents',
                        'industrial': 'Industrial plans',
                        'business': 'Business plans',
                        'contracts': 'Contracts',
                        'documents': 'Documents',
                        'cheque': 'Paychecks',
                        'art': 'Artistic and Creative works',
                        'scientific': 'Research proposals and Papers',
                        'ideas': 'Ideas'
                    },
                    'b': {
                        'a': {
                            'title': 'Receive certificates without exposing your files',
                            'body': 'No upload required and no third party involved in issuing your unique and provable certificates'
                        },
                        'b': {
                            'title': 'Share your works without fear of being stolen',
                            'body': 'Authentication and verification of your certificates is always accessible which allows you to easily prove your ownership at anytime'
                        },
                        'c': {
                            'title': 'Manipulation of certificates is impossible',
                            'body': 'Your certificates will be stored in Utabit blockchain which provides maximum immutability and security'
                        },
                        'd': {
                            'title': 'Detect changes in your documents',
                            'body': 'Worried about unwanted changes in your documents? Just compare stored certificates to make sure about integrity'
                        }
                    },
                    'c': {
                        'a': {
                            'title': 'Safe',
                            'body': 'Your certificates will be hosted by a network of powerful servers spread across the world'
                        },
                        'b': {
                            'title': 'Protected',
                            'body': 'No one including Utadoc can access your data by having your certificate since it only contains the file fingerprint'
                        },
                        'c': {
                            'title': 'Transparent',
                            'body': 'Utabit blockchain is a decentralized platform which allows universal access with the highest transparency level'
                        },
                        'd': {
                            'title': 'Cheap',
                            'body': 'Registration and issuance fee for certificates is very low regarding its intellectual and monetary values'
                        },
                        'e': {
                            'title': 'Immutable',
                            'body': 'Once your certificates registered and stored on Utabit blockchain, No one can never, EVER edit or delete them'
                        },
                        'f': {
                            'title': 'Global',
                            'body': 'Anyone from anywhere can easily check the authenticity of your certificates'
                        },
                        'g': {
                            'title': 'Credible',
                            'body': 'Utabit blockchain guarantees the creation and ownership of your files with timestamping'
                        },
                        'h': {
                            'title': 'Fast',
                            'body': 'It only takes 15 minutes to receive your certificate'
                        },
                        'i': {
                            'title': 'Accesible',
                            'body': 'Without any necessity or requirements connect to Utadoc using a computer or a smartphone and request a certificate for your files'
                        }
                    }
                },
                'footer': {
	                'using_utadoc': 'Using Utadoc',
	                'more_info': 'More about us',
	                'download_app': 'Download App',
                    'desc': 'The first Document Authentication and Timestamping System (Utadoc) for different kinds of digital contents such as documents, plans, ideas as well as business, scientific and artistic works in the immutable platform of blockchain',
                    'help': 'Help',
                    'faq': 'FAQ',
                    'terms': 'Terms and conditions',
                    'privacy': 'Privacy policy',
                    'whitepaper': 'White Paper',
                    'aboutus': 'About us',
                    'enterprise': 'Enterprise solutions',
                    'contact': 'Contact us',
                    'job': 'Job Opportunities'
                },
                'upload': {
                    'totalyoffline': 'You do not need to upload your files, processing of fingerprints is completely offline',
                    'choose': 'Choose your file',
                    'drag': 'or drag it here',
                    'wait': 'Please wait...',
                    'desc': 'When your files are registered in Utadoc, You can prove your ownership anytime anywhere'
                },
                'sidePages': {
                    'about': {
                        'title': 'About us',
                        'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                    },
                    'whitePaper': {
                        'title': 'White Paper',
                        'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                    },
                    'privacy': {
                        'title': 'Privacy Policy',
                        'body': {
                            'line1': ' Using our services means that you agree to follow all the required rules.',
                            'line2': ' Don’t misuse our Services. For example, don’t interfere with our Services or try to access them using a method other than the interface and the instructions that we provide. You may use our Services only as permitted by law, including applicable export and re-export control laws and regulations. We may suspend or stop providing our Services to you if you do not comply with our terms or policies or if we are investigating suspected misconduct.',
                            'line3': ' Using our Services does not give you ownership of any intellectual property rights in our Services or the content you access. You may not use content from our Services unless you obtain permission from its owner or are otherwise permitted by law.',
                            'line4': ' These terms do not grant you the right to use any branding or logos used in our Services.',
                            'line5': ' Don’t remove, obscure, or alter any legal notices displayed in or along with our Services.',
                            'line6': ' You may need a Google Account in order to use some of our Services. To protect your Google Account, keep your password confidential. You are responsible for the activity that happens on or through your Google Account. Try not to reuse your Google Account password on third-party applications.',
                            'line7': ' Google’s privacy policies explain how we treat your personal data and protect your privacy when you use our Services.',
                            'line8': ' By using our Services, you agree that Google can use such data in accordance with our privacy policies.',
                            'line9': ' Some of our Services allow you to upload, submit, store, send or receive content. You retain ownership of any intellectual property rights that you hold in that content. In short, what belongs to you stays yours.',
                            'line10': ' We are constantly changing and improving our Services. We may add or remove functionalities or features, and we may suspend or stop a Service altogether.',
                            'line11': ' We believe that you own your data and preserving your access to such data is important. If we discontinue a Service, where reasonably possible, we will give you reasonable advance notice and a chance to get information out of that Service.',
                            'line12': ' We may modify these terms or any additional terms that apply to a Service to, for example, reflect changes to the law or changes to our Services. You should look at the terms regularly. We’ll post notice of modifications to these terms on this page. We’ll post notice of modified additional terms in the applicable Service. Changes will not apply retroactively and will become effective no sooner than fourteen days after they are posted. However, changes addressing new functions for a Service or changes made for legal reasons will be effective immediately. If you do not agree to the modified terms for a Service, you should discontinue your use of that Service.',
                            'line13': ' If there is a conflict between these terms and the additional terms, the additional terms will control for that conflict.',
                        }
                    },
                    'terms': {
                        'title': 'Terms and Conditions',
                        'body': {
                            'line1': ' Using our services means that you agree to follow all the required rules.',
                            'line2': ' Don’t misuse our Services. For example, don’t interfere with our Services or try to access them using a method other than the interface and the instructions that we provide. You may use our Services only as permitted by law, including applicable export and re-export control laws and regulations. We may suspend or stop providing our Services to you if you do not comply with our terms or policies or if we are investigating suspected misconduct.',
                            'line3': ' Using our Services does not give you ownership of any intellectual property rights in our Services or the content you access. You may not use content from our Services unless you obtain permission from its owner or are otherwise permitted by law.',
                            'line4': ' These terms do not grant you the right to use any branding or logos used in our Services.',
                            'line5': ' Don’t remove, obscure, or alter any legal notices displayed in or along with our Services.',
                            'line6': ' You may need a Google Account in order to use some of our Services. To protect your Google Account, keep your password confidential. You are responsible for the activity that happens on or through your Google Account. Try not to reuse your Google Account password on third-party applications.',
                            'line7': ' Google’s privacy policies explain how we treat your personal data and protect your privacy when you use our Services.',
                            'line8': ' By using our Services, you agree that Google can use such data in accordance with our privacy policies.',
                            'line9': ' Some of our Services allow you to upload, submit, store, send or receive content. You retain ownership of any intellectual property rights that you hold in that content. In short, what belongs to you stays yours.',
                            'line10': ' We are constantly changing and improving our Services. We may add or remove functionalities or features, and we may suspend or stop a Service altogether.',
                            'line11': ' We believe that you own your data and preserving your access to such data is important. If we discontinue a Service, where reasonably possible, we will give you reasonable advance notice and a chance to get information out of that Service.',
                            'line12': ' We may modify these terms or any additional terms that apply to a Service to, for example, reflect changes to the law or changes to our Services. You should look at the terms regularly. We’ll post notice of modifications to these terms on this page. We’ll post notice of modified additional terms in the applicable Service. Changes will not apply retroactively and will become effective no sooner than fourteen days after they are posted. However, changes addressing new functions for a Service or changes made for legal reasons will be effective immediately. If you do not agree to the modified terms for a Service, you should discontinue your use of that Service.',
                            'line13': ' If there is a conflict between these terms and the additional terms, the additional terms will control for that conflict.',
                        }
                    },
                    'job': {
                        'title': 'Job opportunities',
                        'body1': 'Ready to build the future?',
                        'body2': ' In AREATAK, we are programming the future by developing unprecedented and revolutionary solutions for organizations. If you are a genius coder whose capabilities are not discovered yet or an expert researcher in the field of technology who thinks beyond ordinary technologies, we have challenging and attractive jobs for you.',

                        'body3': 'Send an email to prove your skills and claim your job!'
                    },
                    'help': {
                        'title': 'Help',
                        'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                    },
                    'enterprise': {
                        'title': 'Enterprise Solutions',
                        'body': 'Lorem ipsum dolor sit amet, iisque iracundia aliquando ea eos, an qui facete tractatos. Sit ei nostrud numquam luptatum. Duo adversarium ullamcorper at, assum voluptatum at vel, ex eum reque postulant. Mei et debitis impedit ancillae, duo percipit honestatis eu, ignota integre mea id.'
                    },
                    'contact': {
                        'addressTitle': 'Address:',
                        'address': ' Unit 7-No 210- Dastgerdi (Zafar) St – Tehran - Iran',
                        'phoneTitle': 'Phone:'
                    },
                    'invite': {
                        'title': 'Invite Friends',
                        'sendButton': 'Send Email'
                    },
                    'faq': {
                        'title': 'FAQ',
                        'item1': {
                            'question': 'Can I request certificates for all kinds of files extensions?',
                            'answer': 'Yes. You can request certificates for every file extensions including text, music, movie etc.'
                        },
                        'item2': {
                            'question': 'Does Utadoc access my documents?',
                            'answer': 'No. Although you can upload a copy of your file in our servers, it is not mandatory. The fingerprint processing is also completely offline.'
                        },
                        'item3': {
                            'question': 'Is it possible for a third party to access the content of my document with my fingerprint?',
                            'answer': 'No. in Utadoc we use the most reliable cryptographic solutions that provide an impenetrable file fingerprint.'
                        },
                        'item4': {
                            'question': 'How can I make sure Utadoc is always up and running?',
                            'answer': 'Since your certificates stored in Utabit blockchain, in case of shutting down Utadoc you can still use Utabit blockchain explorers to access yours.'
                        },
                        'item5': {
                            'question': 'How long does it take to verify a certificate?',
                            'answer': 'Once you paid the registration fee Utadoc begins generating your unique file fingerprint which takes almost 10 minutes. However, in some instances it may require up to 60 minutes.'
                        },
                        'item6': {
                            'question': 'How can I use my certificates?',
                            'answer': 'Your certificates work like authenticators that show your ownership rights over your files. Comparing your certificate with file fingerprint can be done in Utabit blockchain explorers. When both matched, you can prove that you created the file in a specific time and its content has not changed yet.'
                        },
                        'item7': {
                            'question': 'Will my certificate remain the same if I change the content of my file?',
                            'answer': 'No. even the slightest changes in your file content would completely change its fingerprint. Thus, save your files in a secure place like Utadoc servers and keep backup copies at your disposal.'
                        },
                        'item8': {
                            'question': 'How can I use my Utadoc certificate in legal conflicts?',
                            'answer': ' provides a valid and reliable certificate to prove your ownership. It also clarifies that whether the content of a document has manipulated or not. However, its acceptance by law has been a controversial issue. We strongly recommend that you consult with your lawyer before using your certificates as evidences.'
                        }
                    }
                },
                'payment': {
                    'buyCredit': 'Buy Credit',
                    'amount': 'Amount',
                    'amountUtabit': 'Amount(Ubit)',
                    'amountRial': 'Amount(IRRial)',
                    'paymentButton': 'Payment',
                    'paymentResult': 'Payment Result',
                    'paymentAddress': 'Payment Address',
                    'paymentAddressDesc': 'Please scan the QR Code.',
                    'invoice': 'transaction tracking number',
                    'yourCredit': 'Your Credit',
                    400: 'Requested Utabit amount is less than min.',
                    'resultMsg': {
                        'timeLeft': 'Estimated time for verifying your payment: between ',
                        'whenDone': 'After payment confirmed, your account credit will be charged.'
                    }
                },
                'profile': {
                    'title': 'Profile',
                    'name': 'Name',
                    'family': 'Family',
                    'currentPass': 'Current Password',
                    'changePass': 'Change Password',
                    'edit': 'Edit',
                    200: 'Profile successfully updated.',
                    204: 'Your identity information is not valid.',
                    400: 'Bad request.',
                    403: 'Your current password is incorrect.',
                    500: 'Updating your profile interrupted by an error.',

                },
                'passwordRecover': {
                    'forgotPassword': 'Forgot Password',
                    'recoverPassword': 'Recover Password',

                },
                'recover': {
                    'expired': 'Your request is expired.',
                    'invalidData': 'Data for password recovery is invalid.',
                    'changeException': 'We cannot recover the password.'
                },
                'signin': {
                    'signup': 'Create a free account',
                    'signin': 'I have an account',
                    'forgotPassword': 'Forgot your password?',
                    200: 'Successfully logged in.',
                    204: 'Incorrect username or password.',
                    401: 'Incorrect username or password.',
                    400: 'Bad request.'
                },
                'signup': {
                    200: 'Successfully registered.',
                    204: 'Required field is empty.',
                    226: 'The email address you entered is already registered.Please enter another email address.',
                    400: 'Bad request.',
                    412: 'Your password and confirmation password do not match.'
                },
                'transaction': {
                    'showDetail': 'More Details',
                    'invalidDoc': 'Invalid Document!',
                    'createTx': 'Create Transaction',
                    'fileUploaded1': 'Upload Files on ',
                    'fileUploaded2': ' servers.',
                    'detail': {
                        'title': 'Transaction Details',
                        204: 'No transaction matches this number.'
                    },
                    'download': 'Download Document',
                    'relativeTime': {
                        'day': ' day,',
                        'hour': ' hour,',
                        'minute': ' min,',
                        'second': ' sec',
                        'ago': ' ago',

                    },
                    'status': {
                        'waiting': 'Pending',
                        'confirmed': 'Confirmed'
                    },
                    'remainingTime': {
                        'leftMsg': 'Estimated time for verifying transaction: between ',
                        'to': ' to ',
                        'minute': ' min'
                    },
                    'list': {
                        'title': 'Transaction List',
                        400: 'Chosen time period is incorrect.'
                    },
                    'showAll': 'Show All',
                    'fromDate': 'From date',
                    'toDate': 'To date',
                    'create': {
                        1001:'Please select one of the document types.',
                        500: 'Uploading file or registering transaction interrupted by an error.Please try again.',
                        400: 'Bad request.',
                        208: 'This file is already registered. Registering again is impossible.',
                        507: 'Your account balance is not enough.',
                        509: 'Server is currently overloaded. Please try again'
                    },
                    'validate': {
                        404: 'This document is not already registered.'
                    }
                },
                'general': {
                    'words': {
                        'signin': 'sign in',
                        'signup': 'sign up',
                        'email': 'email',
                        'password': 'password',
                        'confirmPassword': 'confirm password',
                        'search': 'search',
                        'okButton': 'OK',
                        'close': 'close',
                        'submit': 'submit',
                        'desc': 'description',
                        'hash': 'Hash',
                        'id': 'ID',
                        'status': 'status',
                        'date': 'date',
                        'detail': 'details',
                        'file': 'file',
                        'clear': 'clear',
	                    'language':'Language'
                    },
                    'input': {
                        'required': 'This field is required.',
                        'validEmail': 'Please enter a valid email address.',
                        'minlength': {
                            '6': 'At least enter 6 characters.'
                        }
                    },
                    'error': {
                        500: 'Error! Please try again.'
                    }
                },
                'auth': {
                    'google': {
                        'permissionDenied': '"Utadoc" cannot access Google.',
                        'inviteError': 'We cannot send you the invitation.'
                    }
                }
            }
        );

        $translateProvider.preferredLanguage('en');
    }]);