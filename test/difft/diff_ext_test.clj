(ns difft.diff-ext-test
  (:require [difft.diff :refer :all]
            [midje.sweet :refer :all]))

(comment
  ;; bench, 测试比用google diff快8倍左右,当然ddiff不会比较字符串内容
  (use 'criterium.core)

  (with-progress-reporting
    (quick-bench
     (print-html (ddiff/diff
                  {:registrant-organization "Beijing Baidu Netcom Science Technology Co., Ltd.",
                   :dnssec "unsigned",
                   :updated-date "2019-05-08T20:59:33-0700",
                   :domain-status
                   '("clientUpdateProhibited"
                     "clientTransferProhibited"
                     "clientDeleteProhibited"
                     "serverUpdateProhibited"
                     "serverTransferProhibited"
                     "serverDeleteProhibited"),
                   :name-server
                   '("ns4.baidu.com"
                     "ns3.baidu.com"
                     "ns7.baidu.com"
                     "ns1.baidu.com"
                     "ns2.baidu.com"),
                   :tech-email
                   "Select Request Email Form at https://domains.markmonitor.com/whois/baidu.com",
                   :url-of-the-icann-whois-data-problem-reporting-system
                   "http://wdprs.internic.net/",
                   :registrant-email
                   "Select Request Email Form at https://domains.markmonitor.com/whois/baidu.com",
                   :registrar "MarkMonitor, Inc.",
                   :tech-state/-province "Beijing",
                   :admin-email
                   "Select Request Email Form at https://domains.markmonitor.com/whois/baidu.com",
                   :admin-country "CN",
                   :registry-domain-id "11181110_DOMAIN_COM-VRSN",
                   :registrar-url "http://www.markmonitor.com",
                   :registrant-country "CN",
                   :registrar-abuse-contact-email "abusecomplaints@markmonitor.com",
                   :tech-organization "Beijing Baidu Netcom Science Technology Co., Ltd.",
                   :registrar-whois-server "whois.markmonitor.com",
                   :registrar-iana-id "292",
                   :domain-name "baidu.com",
                   :registrant-state/-province "Beijing",
                   :creation-date "1999-10-11T04:05:17-0700",
                   :registrar-registration-expiration-date "2026-10-11T00:00:00-0700",
                   :tech-country "CN",
                   :registrar-abuse-contact-phone "+1.2083895770",
                   :admin-organization "Beijing Baidu Netcom Science Technology Co., Ltd.",
                   :admin-state/-province "Beijing"}
                  {:registrant-organization "Beijing Baidu Netcom Science Technology Co., Ltd.",
                   :dnssec "unsigned",
                   :updated-date "2019-05-08T20:59:33-0700",
                   :domain-status
                   '("clientUpdateProhibited"
                     "clientTransferProhibited"
                     "serverTransferProhibited"
                     "serverDeleteProhibited"),
                   :name-server
                   '("ns4.baidu.com"
                     "ns9.baidu.com"
                     "ns1.baidu.com"
                     "ns2.baidu.com"),
                   :tech-email
                   "Select Request Email Form at https://domains.markmonitor.com/whois/baidu.com",
                   :url-of-the-icann-whois-data-problem-reporting-system
                   "http://wdprs.internic.net/",
                   :registrant-email
                   "Select Request Email Form at https://domains.markmonitor.com/whois/baidu.com",
                   :registrar "MarkMonitor, Inc.",
                   :tech-state/-province "Beijing",
                   :admin-email
                   "Select Request Email Form at https://domains.markmonitor.com/whois/baidu.com",
                   :admin-country "EN",
                   :registry-domain-id "11181110_DOMAIN_COM-VRSN",
                   :registrar-url "http://www.markmonitor.com",
                   :registrant-country "CN",
                   :registrar-abuse-contact-email "abusecomplaints@markmonitor.com",
                   :tech-organization "Beijing Baidu Netcom Science Technology Co., Ltd.",
                   :registrar-whois-server "whois.markmonitor.com",
                   :registrar-iana-id "292",
                   :domain-name "goole.com",
                   :registrant-state/-province "Beijing",
                   :creation-date "1999-10-11T08:05:17-0700",
                   :registrar-registration-expiration-date "2026-10-11T00:00:00-0700",
                   :tech-country "CN",
                   :registrar-abuse-contact-phone "+1.2083895770",
                   :admin-organization "Beijing Baidu Netcom Science Technology Co., Ltd.",
                   :admin-state/-province "Beijing"}

                  ))
     :verbose))

  )
