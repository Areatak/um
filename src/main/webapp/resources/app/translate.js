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
                'banner': {
                    'title': 'United Muniments',
                    'body': 'Unlimited Independent Global Registration Network'
                },
                'safe': {
                    'title': 'Feel Safe',
                    'body1': 'For centuries, registering the ownership of a work of creativity was the most important concern of humans.As human knowledge has increased, life is getting more complicated and the security of registration has become more difficult.',
                    'body2': 'At times far away, the writing was enough to record. Later, registration required certification and testimony. Humans invented the seal, special papers, and even holograms. International registration associations formed based on protocols between countries. But no matter how complicated it gets, it is not enough yet. Especially in the period that the data is more precious than gold.',
                    'body3': 'Many people have some problems in recording their intellectual properties: first of all, the registration process is doing by a third party. Second, this is a long and complicated process in most of international systems. And third is the cost of recording a work is very expensive.  Even if you don’t have any problem with cost and payments, but you will always have some concerns about the error can happen by the performance of third party and another person can register your item or even access to yours.',
                    'body4': 'UM is the beginning of a new period of registration. Registering and saving the files is not affiliated with anyone anymore.',
                    'body5': 'With UM technology, each uploaded file immediately converts to an international code (Regarding SHA 256 Protocol), which will not be readable by anyone, unless you.',
                    'body6': 'The Blockchain network is a technology of future, which came to help us here. The unique code of your file (We call it Hash) plus the time stamp of uploading files will be recorded on all related computers around the world on the Utabit Blockchain Network and nobody can delete or change this global consensus. In this stage, your file (by its unique code) is recorded internationally but nobody can see or have access to it.',
                    'body7': 'You will be permanently the first person who had that file and no one can ignore your claims on it. Also, your file will be saved on your personal panel on UM’s server to be used as testify in the future.',
                    'body8': 'Please notice: With using Utabit App you can join to our global testifier’s network. Then you will have an updated database of all global entries too.'
                },
                'feature': {
                    '1': {
                        'title': 'Safe',
                        'body': 'Your certificates will be hosted by a network of powerful servers spread across the world'
                    },
                    '2': {
                        'title': 'Protected',
                        'body': 'No one including UM can access your data by having your certificate since it only contains the file fingerprint'
                    },
                    '3': {
                        'title': 'Transparent',
                        'body': 'UM Blockchain is a decentralized platform, which allows universal access with the highest transparency level'
                    },
                    '4': {
                        'title': 'Cheap',
                        'body': 'Registration and issuance fee for certificates is very low regarding its intellectual and monetary values'
                    },
                    '5': {
                        'title': 'Immutable',
                        'body': 'Once your certificates registered and stored on UM Blockchain, No one can never, EVER edit or delete them'
                    },
                    '6': {
                        'title': 'Global',
                        'body': 'Anyone from anywhere can easily check the authenticity of your certificates'
                    },
                    '7': {
                        'title': 'Credible',
                        'body': 'UM Blockchain guarantees the creation and ownership of your files with time stamping'
                    },
                    '8': {
                        'title': 'Fast',
                        'body': 'It only takes 15 minutes to receive your certificate'
                    },
                    '9': {
                        'title': 'Accessible',
                        'body': 'Without any necessity or requirements connect to UM using a computer or a smartphone and request a certificate for your files'
                    },

                },
                'services': {
                    'title': 'Utadoc do your work'
                },
                'adv': {
                    'title': 'Why Utadoc?'
                }
            },
            'footer': {
                'using_utadoc': 'Using Utadoc',
                'more_info': 'More about us',
                'download_app': 'Download App',
                'desc': 'The first unlimited independent global registration network based on blockchain platform powered by MarcommPub and Utabit. All rights resereved for MarcommPub B.Y.R Ltd. 2017',
                'help': 'Help',
                'faq': 'FAQ',
                'terms': 'Terms and conditions',
                'privacy': 'Privacy policy',
                'whitepaper': 'White Paper',
                'aboutus': 'About us',
                'enterprise': 'Enterprise solutions',
                'contact': 'Contact us:',
                'email': 'support@um.international',
                'job': 'Job Opportunities'
            },
            'upload': {
                'totalyoffline': 'Register Your File Now:',
                'validate_file': 'Validate your file:',
                'validate_desc': 'Choose or Drag & Drop your file to perform a search on finger print of the file.',
                'choose': 'Choose a file',
                'drag': 'Drag & Drop',
                'wait': 'Please wait...',
                'desc-title': 'Let\'s Know More:',
                'desc1': 'SHA 256 Protocol is a set of cryptographic hash functions designed by the United States National Security Agency (NSA). Cryptographic hash functions are mathematical operations run on digital data; by comparing the computed "hash" (the output from execution of the algorithm) to a known and expected hash value, a person can determine the integrity of data. For example, computing the hash of a downloaded file and comparing the result to a previously published hash result can show whether the download has been modified or tampered with. A key aspect of cryptographic hash functions is their collision resistance: nobody should be able to find two different input values that result in the same hash output.',
                'desc2': 'A blockchain is a continuously growing list of records, called blocks, which are linked and secured using cryptography. Each block typically contains a hash pointer as a link to a previous block, a timestamp and transaction data. By design, blockchains are inherently resistant to modification of the data. A blockchain can serve as an open, distributed ledger that can record transactions between two parties efficiently and in a verifiable and permanent way. For use as a distributed ledger, a blockchain is typically managed by a peer-to-peer network collectively adhering to a protocol for validating new blocks. Once recorded, the data in any given block cannot be altered retroactively without the alteration of all subsequent blocks, which needs a collusion of the network majority.',
                'desc3': 'Blockchains are secure by design and are an example of a distributed computing system with high Byzantine fault tolerance. Decentralized consensus has therefore been achieved with a blockchain. This makes blockchains potentially suitable for the recording of events, medical records, and other records management activities, such as identity management, transaction processing and documenting provenance. '
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
                'fieldGroup': {
                    'company': 'Company',
                    'account': 'Account',
                    'creativeDirector': 'Creative Director'
                },
                'companyName': 'Company Name',
                'companyActivity': 'Company Activity',
                'postalAddress': 'Postal Address',
                'city': 'City',
                'postCode': 'Post Code',
                'country': 'Country',
                'companyPhone': 'Company Phone',
                'companyWebsite': 'Company Website',
                'cdName': '',
                '': '',

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
                    1001: 'Please select one of the document types.',
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
                    'language': 'Language'
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

        $translateProvider.preferredLanguage('en');
    }]);