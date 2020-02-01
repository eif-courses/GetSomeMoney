package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Adapters.ParagraphListAdapter
import get.some.money.starter.Models.Paragraph
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import kotlinx.android.synthetic.main.fragment_privacy_policy.*


class PrivacyPolicyFragment : Fragment(R.layout.fragment_privacy_policy){

  lateinit var recyclerView: RecyclerView
  lateinit var paragraphListAdapter: ParagraphListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    recyclerView = privacy_policy_recycleView

    recyclerView.layoutManager = LinearLayoutManager(context)

    paragraphListAdapter = ParagraphListAdapter()


    val list = ArrayList<Paragraph>()

    if(!Language.getCurrentLanguage().equals("lt")) {
      list.add(
        Paragraph(
          "Privacy Policy",
          "It Works built the Logic for fun app as an Ad Supported app. This SERVICE is provided by It Works at no cost and is intended for use as is.\n" +
              "\n" +
              "This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.\n" +
              "\n" +
              "If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.\n" +
              "\n" +
              "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Logic for fun unless otherwise defined in this Privacy Policy."
        )
      )
      list.add(
        Paragraph(
          "Information Collection and Use",
          "For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information. The information that I request will be retained on your device and is not collected by me in any way.\n" +
              "\n" +
              "The app does use third party services that may collect information used to identify you.\n" +
              "\n" +
              "Link to privacy policy of third party service providers used by the app\n" +
              "\n" +
              "Google Play Services: https://www.google.com/policies/privacy/\n" +
              "Firebase Analytics: https://firebase.google.com/policies/analytics\n" +
              "Firebase Crashlytics: https://firebase.google.com/terms/crashlytics"
        )
      )
      list.add(
        Paragraph(
          "Log Data",
          "I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics."
        )
      )

      list.add(
        Paragraph(
          "Cookies",
          "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
              "\n" +
              "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service."
        )
      )
      list.add(
        Paragraph(
          "Service Providers",
          "I may employ third-party companies and individuals due to the following reasons:\n" +
              "\n" +
              "To facilitate our Service;\n" +
              "To provide the Service on our behalf;\n" +
              "To perform Service-related services; or\n" +
              "To assist us in analyzing how our Service is used.\n" +
              "I want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose."
        )
      )
      list.add(
        Paragraph(
          "Security",
          "I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security."
        )
      )
      list.add(
        Paragraph(
          "Links to Other Sites",
          "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services."
        )
      )
      list.add(
        Paragraph(
          "Children’s Privacy",
          "These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do necessary actions."
        )
      )
      list.add(
        Paragraph(
          "Changes to This Privacy Policy",
          "I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately after they are posted on this page."
        )
      )
      list.add(
        Paragraph(
          "Contact Us",
          "If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at marius.gzegozevskis@gmail.com."
        )
      )
    }else{
      list.add(
        Paragraph(
          "Privatumo politika",
          "It Works sukurta mobilioji programėlė Logic for fun (Logika linksmai) yra palaikoma reklaminių skelbimų. Visos paslaugos programėlėje yra neapmokestintos.\n" +
              "Šis puslapis yra naudojamas informuoti lankytojus apie asmeninės informacijos rinkimo, naudojimo politiką besinaudojantiems šia programėle asmenims.\n" +
              "\n" +
              "Jei nuspręsite naudoti šią mobilią programėlę Logika linksmai, tada jūs sutinkate su šia politika rinkti ir naudoti informaciją. Asmeninė informacija, kuri bus surinkta, naudojama teikiant ir tobulinant mobiliąją programėlę. Jūsų asmeninė informacija niekada nebus panaudota, išskyrus tuos atvejus, aprašytus šioje privatumo politikoje.\n" +
              "\n" +
              "Šioje privatumo politikoje vartojamos sąvokos turi tas pačias reikšmes, kaip ir mūsų sąlygos ir nuostatose, kurias Logic for fun (Logika linksmai)."
        )
      )
      list.add(
        Paragraph(
              "Informacijos rinkimas ir naudojimas",
              "Siekiant gerenti žaidimo kokybę gali būti prašomą papildoma asmeninė informacija.\n" +
              "Programa naudojasi trečiųjų šalių paslaugomis, kurios gali rinkti informaciją, naudojamą identifikuoti jus.\n" +
              "Nuoroda į programos naudojamų trečiųjų šalių paslaugų teikėjų privatumo politiką:\n" +
              "Google Play Services: https://www.google.com/policies/privacy/\n" +
              "Firebase Analytics: https://firebase.google.com/policies/analytics\n" +
              "Firebase Crashlytics: https://firebase.google.com/terms/crashlytics"
        )
      )
      list.add(
        Paragraph(
          "Duomenų registravimas žurnale",
          "Noriu jus informuoti, kad kai naudojatės mobiliaja programėle Logic for fun, programos klaidos atveju yra renkami duomenis ir informaciją (per trečiųjų šalių produktus) į jūsų telefoną, vadinamą „Log Data“. Į šiuos žurnalo duomenis gali būti įtraukta tokia informacija kaip jūsų įrenginio interneto protokolo („IP“) adresas, įrenginio pavadinimas, operacinės sistemos versija, programos konfigūracija, kai naudojate šią programėlę, programėlės naudojimo laikas ir data bei kita statistika."
        )
      )

      list.add(
        Paragraph(
          "Slapukai",
          "Slapukai yra failai su nedideliu duomenų kiekiu skirti papildomai suteikti vartotojui patogumą naudojantis programėlę. Kurie paprastai naudojami kaip anoniminiai unikalūs identifikatoriai. Jie siunčiami į jūsų naršyklę iš jūsų lankomų svetainių ir yra saugomi jūsų įrenginio vidinėje atmintyje.\n" +
              "Logic for fun (Logika linksmai) programėlė nenaudoja slapukų. Tačiau programa gali naudoti trečiųjų šalių kodus ir bibliotekas, kurios naudoja „slapukus“ informacijai rinkti ir savo paslaugoms tobulinti. Jūs turite galimybę priimti arba atsisakyti šių slapukų ir žinoti, kada slapukas siunčiamas į jūsų įrenginį. Slapukai yra pagalbiniai komponentai be kurių gali neveikti kai kurios paslaugos."
        )
      )
      list.add(
        Paragraph(
          "Paslaugų tiekėjai",
              "Aš galiu įdarbinti trečiųjų šalių įmones ar asmenis dėl šių priežasčių:\n" +
                  "Pagerinti mūsų aptarnavimą;\n" +
              "Teikti paslaugą mūsų vardu;\n" +
                  "Atlikti analizę su programėle susijusias paslaugas arba analizuoti, kaip naudojama mobilioji programėlė.\n" +
                  "Noriu informuoti šios programėlės vartotojus, kad programėlėje trečiosios šalys turi prieigą prie jūsų asmeninės informacijos tik mūsų kompanijos vardu jiems paskirtas užduotis. Su sąlyga jie yra įpareigoti neatskleisti ir nenaudoti informacijos jokiems kitiems tikslams."
        )
      )
      list.add(
        Paragraph(
          "Saugumas",
          "Mes vertiname jūsų pasitikėjimą teikiant mums jūsų asmeninę informaciją, todėl mes stengiamės naudoti komerciškai priimtinas priemones jai apsaugoti. Bet atminkite, kad nė vienas perdavimo internetu būdas ar elektroninio saugojimo būdas nėra 100% saugus ir patikimas ir mes negalime garantuoti jo visiško saugumo."
        )
      )
      list.add(
        Paragraph(
              "Nuorodos į kitas svetaines",
              "Šioje mobiliojoje programėlėje gali būti nuorodų į kitas svetaines. Jei spustelėsite trečiosios šalies nuorodą, būsite nukreipti į tą svetainę. Atminkite, kad šios išorinės svetainės nėra mūsų kontroliuojamos. Todėl labai patariu peržiūrėti šių svetainių privatumo politiką. Mes nekontroliuojame ir neprisiimame jokios atsakomybės už trečiųjų šalių svetainių ar paslaugų turinį, privatumo politiką ar praktiką."
        )
      )
      list.add(
        Paragraph(
          "Vaikų privatumas",
          "Ši programėlė nėra suinterisuota į jaunesnius nei 13 metų asmenis. Programėlė nerenka asmeniškai identifikuojamą informaciją iš vaikų iki 13 metų. Jeigu bus nustatyta, kad vaikas iki 13 metų man pateikė asmeninę informaciją, ji bus ištrinta iš mūsų serverių. Jei esate vienas iš tėvų ar globėjų ir žinote, kad jūsų vaikas pateikė mums asmeninės informacijos, susisiekite su mumis el. paštu: marius.gzegozevskis@gmail.com, kad būtų galima atlikti būtinus veiksmus."
        )
      )
      list.add(
        Paragraph(
              "Šios privatumo politikos pakeitimai",
          "Retkarčiais bus atnaujinama mūsų privatumo politiką. Taigi jums patariama periodiškai peržiūrėti šį puslapį, kad atsiradūs pakeitimams su jais susipažintumėte. Aš pranešsiu jums apie visus pasikeitimus, paskelbdamas naują privatumo politiką šiame puslapyje arba informuojant programėlėje pranešimu. Šie pakeitimai įsigalioja iškart po jų paskelbimo šiame puslapyje."
        )
      )
      list.add(
        Paragraph(
          "Susisiekite su mumis",
              "Jei turite klausimų ar pasiūlymų dėl mano privatumo politikos, nedvejodami susisiekite su mumis el. paštu marius.gzegozevskis@gmail.com."
        )
      )
    }


    paragraphListAdapter.swapData(list)
    recyclerView.adapter = paragraphListAdapter





//    shopViewModel = ViewModelProviders.of(this)[ShopViewModel::class.java]
//    userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]

  }
}
