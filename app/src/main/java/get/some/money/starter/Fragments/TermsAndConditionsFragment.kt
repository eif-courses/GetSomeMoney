package get.some.money.starter.Fragments


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import get.some.money.starter.Adapters.ParagraphListAdapter
import get.some.money.starter.Models.Paragraph
import get.some.money.starter.R
import get.some.money.starter.Util.Language
import kotlinx.android.synthetic.main.fragment_terms_and_conditions.*

class TermsAndConditionsFragment : Fragment(R.layout.fragment_terms_and_conditions) {
  lateinit var recyclerView: RecyclerView
  lateinit var paragraphListAdapter: ParagraphListAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    recyclerView = terms_and_conditions_recycleview
    recyclerView.layoutManager = LinearLayoutManager(context)
    paragraphListAdapter = ParagraphListAdapter()




    if (!Language.getCurrentLanguage().equals("lt")) {

      val list = ArrayList<Paragraph>()

      list.add(
        Paragraph(
          "Terms and Conditions",
          "By downloading or using the app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. You’re not allowed to copy, or modify the app, any part of the app, or our trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages, or make derivative versions. The app itself, and all the trade marks, copyright, database rights and other intellectual property rights related to it, still belong to It Works.\n" +
              "\n" +
              "It Works is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app or to charge for its services, at any time and for any reason. We will never charge you for the app or its services without making it very clear to you exactly what you’re paying for."
        )
      )

      list.add(
        Paragraph(
          "Personal data",
          "The Logic for fun app stores and processes personal data that you have provided to us, in order to provide my Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the Logic for fun app won’t work properly or at all."
        )
      )
      list.add(
        Paragraph(
          "Internet connection",
          "You should be aware that there are certain things that It Works will not take responsibility for. Certain functions of the app will require the app to have an active internet connection. The connection can be Wi-Fi, or provided by your mobile network provider, but It Works cannot take responsibility for the app not working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left.\n" +
              "\n" +
              "If you’re using the app outside of an area with Wi-Fi, you should remember that your terms of the agreement with your mobile network provider will still apply. As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third party charges. In using the app, you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming. If you are not the bill payer for the device on which you’re using the app, please be aware that we assume that you have received permission from the bill payer for using the app."
        )
      )
      list.add(
        Paragraph(
          "Our responsibility for services",
          "Along the same lines, It Works cannot always take responsibility for the way you use the app i.e. You need to make sure that your device stays charged – if it runs out of battery and you can’t turn it on to avail the Service, It Works cannot accept responsibility.\n" +
              "\n" +
              "With respect to It Works’s responsibility for your use of the app, when you’re using the app, it’s important to bear in mind that although we endeavour to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. It Works accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.\n" +
              "\n" +
              "At some point, we may wish to update the app. The app is currently available on Android – the requirements for system (and for any additional systems we decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. It Works does not promise that it will always update the app so that it is relevant to you and/or works with the Android version that you have installed on your device. However, you promise to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device."
        )
      )
      list.add(
        Paragraph(
          "Changes to This Terms and Conditions",
          "I may update our Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Terms and Conditions on this page. These changes are effective immediately after they are posted on this page."
        )
      )

      list.add(
        Paragraph(
          "Contact Us",
          "If you have any questions or suggestions about my Terms and Conditions, do not hesitate to contact me at marius.gzegozevskis@gmail.com."
        )
      )
      paragraphListAdapter.swapData(list)
    } else {
      val list = ArrayList<Paragraph>()

      list.add(
        Paragraph(
          "Sąlygos ir nuostatos",
              "Atsisiųsdami ar naudodami šią mobilią programėlę šios sąlygos bus automatiškai pritaikytos jums - todėl prieš naudodamiesi programa, įsitikinkite, kad atidžiai perskaitėte. Jums neleidžiama jokiu būdu kopijuoti ar keisti programos turinį, bet kurios programos dalies ar mūsų prekės ženklų. Jums neleidžiama bandyti išgauti programos išeities tekstų. Taip pat neturėtumėte bandyti programos išversti į kitas kalbas ir panaudoti kaip savo arba kurti išvestinių versijų. Pati programa ir visi su ja susiję prekių ženklai, autorių teisės, duomenų bazių ir kitos intelektinės nuosavybės teisės vis dar priklauso \"It Works\"."
              +
              "\"It works\" pasilieka teisę siekdama užtikrinti, kad programa būtų kuo patogesnė ir naudingesnė keisti turinį papildyti naujomis funkcijomis. Dėl šios priežasties mes pasiliekame teisę bet kuriuo metu ir dėl bet kokios priežasties keisti programą arba imti mokestį už jos paslaugas. Niekada neapmokestinsime jūsų už programą ar jos paslaugas, jei jums nebus labai aišku, už ką mokate."
        )
      )

      list.add(
        Paragraph(
          "Asmeniniai duomenys",
          "Logic for fun \"Logika linksmai\" programėlė saugo ir tvarko asmens duomenis, kuriuos mums pateikėte, kad galėtumėte teikti papildomas paslaugas kaip galimybę išsaugoti žaidimo progresą. Jūs esate atsakingi už telefono saugumą bei prieigos suteikimą prie individualių paslaugų."
        )
      )
      list.add(
        Paragraph(
          "Interneto prieiga",
          "Yra tam tikrų dalykų, už kuriuos \"It works\", kompanija neprisiims atsakomybės pvz.: tam tikroms programos funkcijoms atlikti programoje reikės aktyvaus interneto ryšio. Ryšys gali būti \"Wi-Fi\" arba teikiamas jūsų mobiliojo tinklo teikėjo, tačiau \"It works\" neprisiimdamas atsakomybės už tai, kad programa neveikia visu pajėgumu, jei neturite prieigos prie \"Wi-Fi\" arba neturite likusių mobiliųjų duomenų.\n" +
              "Jei programą naudojate ne per \"Wi-Fi\" ryšį, turėtumėte atsiminti, kad sutarties su mobiliojo tinklo teikėju sąlygos vis tiek bus taikomos. Dėl to jūsų mobiliojo ryšio paslaugų teikėjas gali jus apmokestinti už duomenis, susijusius su ryšio trukme, kol prisijungiate prie programos. Naudodamiesi programa jūs prisiimate atsakomybę už visus tokius mokesčius, įskaitant tarptinklinio ryšio duomenų mokesčius, jei programą naudojate ne savo gimtojoje teritorijoje (t. Y. Regione ar šalyje) neišjungdami duomenų tarptinklinio ryšio. Jei nesate sąskaitos mokėtojas už įrenginį, kuriame naudojate programą, atminkite, kad mes manome jog jūs gavote sąskaitos mokėtojo leidimą naudoti programą."
        )
      )
      list.add(
        Paragraph(
          "Mūsų atsokomybė už teikiamas paslaugas",
          "\"It works\" ne visada gali prisiimti atsakomybę už tai, kaip naudojate programą, ty turite įsitikinti, kad jūsų prietaisas yra įkrautas - jei jo akumuliatoriui trūksta energijos ir negalite jo įjungti, kad galėtumėte naudotis programėlę.\n" +
              "Atsižvelgiant į \"It Works\" atsakomybę už jūsų programos naudojimą, kai naudojate programą, svarbu atsiminti, kad nors ir stengiamės užtikrinti, kad ji visada būtų atnaujinta ir teisinga, mes pasikliaujame trečiosiomis šalimis. \"It works\", neatsako už jokius tiesioginius ar netiesioginius nuostolius, kuriuos patiriate dėl to.\n" +
              "Tam tikru metu galbūt norėsime atnaujinti programą. Šiuo metu programą galima naudoti \"Android OS\"  - gali keistis reikalavimai sistemai (ir bet kurioms papildomoms sistemoms, kurias nusprendžiame išplėsti, kad galėtumėte naudotis programa). Jei norite ir toliau naudoti programą, turėsite atsisiųsti naujinius. \"It Works\", nežada, kad visada atnaujins programą taip, kad ji bus suderinta su jūsų įrenginyje įdiegta \"Android OS\" versija, kurią įdiegėte savo įrenginyje. Tačiau jūs pažadate visada įsidiegti programos atnaujinimus, kai jums bus pasiūlyta. Mes taip pat galime norėti nustoti atnaujinti programėlę ir bet kuriuo metu nutraukti jos publikavimą, nepranešdami apie tai iš anksto."
        )
      )
      list.add(
        Paragraph(
          "Šių sąlygų pakeitimai",
          "Retkarčiais gali būti atnaujintos mūsų taisykles ir nuostatos. Taigi jums patariama periodiškai peržiūrėti šį puslapį, jeigu atsirastų kokių nors pakeitimų. Jus būsite informuoti apie pakeitimus šiais būdais: Šiame puslapyje arba pranešimu mobiliojoje programėlėje. Šie pakeitimai įsigalioja iškart po jų paskelbimo šiame puslapyje."
        )
      )

      list.add(
        Paragraph(
          "Susisiekite su mumis",
              "Jei turite klausimų ar pasiūlymų dėl šių taisyklių ir nuostatų, nedvejodami susisiekite su manimi el. paštu marius.gzegozevskis@gmail.com."
        )
      )
      paragraphListAdapter.swapData(list)
    }

    recyclerView.adapter = paragraphListAdapter
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val action = TermsAndConditionsFragmentDirections.actionTermsAndConditionsFragmentToHomeFragment()
    val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      findNavController().navigate(action)
    }
    callback.isEnabled
  }
}
