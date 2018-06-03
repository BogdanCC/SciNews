package com.example.android.spacenews;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class FeaturedNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{

    private View fragmentLayout;
    private static final String customJson = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":5989,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":599,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"science/2011/nov/14/wavewatchers-companion-gavin-pretor-pinney\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2011-11-14T12:14:20Z\",\"webTitle\":\"The Wavewatcher's Companion by Gavin Pretor-Pinney – review\",\"webUrl\":\"https://www.theguardian.com/science/2011/nov/14/wavewatchers-companion-gavin-pretor-pinney\",\"apiUrl\":\"https://content.guardianapis.com/science/2011/nov/14/wavewatchers-companion-gavin-pretor-pinney\",\"fields\":{\"headline\":\"The Wavewatcher's Companion by Gavin Pretor-Pinney – review\",\"trailText\":\"<p>A witty, discursive and entertaining guide to entities that control our lives in ways we rarely appreciate: waves</p>\",\"wordcount\":\"613\"},\"tags\":[{\"id\":\"profile/robinmckie\",\"type\":\"contributor\",\"webTitle\":\"Robin McKie\",\"webUrl\":\"https://www.theguardian.com/profile/robinmckie\",\"apiUrl\":\"https://content.guardianapis.com/profile/robinmckie\",\"references\":[],\"bio\":\"<p>Robin McKie is <a href=\\\"http://www.guardian.co.uk/science\\\">science</a> and <a href=\\\"http://www.guardian.co.uk/technology\\\">technology </a> editor for the <a href=\\\"http://observer.guardian.co.uk/\\\">Observer</a></p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Observer/Columnist/Columnists/2010/10/23/1287830920805/Robin-McKie-001.jpg\",\"firstName\":\"mckie\",\"lastName\":\"\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/grrlscientist/2012/mar/14/4\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2012-03-14T18:43:00Z\",\"webTitle\":\"Principles of Biology [Book Review] | GrrlScientist\",\"webUrl\":\"https://www.theguardian.com/science/grrlscientist/2012/mar/14/4\",\"apiUrl\":\"https://content.guardianapis.com/science/grrlscientist/2012/mar/14/4\",\"fields\":{\"headline\":\"Principles of Biology [Book Review]\",\"trailText\":\"<p>A new introductory biology textbook has been published that is affordable, lightweight and never ever goes out of date</p>\",\"wordcount\":\"1876\"},\"tags\":[{\"id\":\"profile/grrlscientist\",\"type\":\"contributor\",\"webTitle\":\"GrrlScientist\",\"webUrl\":\"https://www.theguardian.com/profile/grrlscientist\",\"apiUrl\":\"https://content.guardianapis.com/profile/grrlscientist\",\"references\":[],\"bio\":\"<p>GrrlScientist is an evolutionary biologist and ornithologist who writes about evolution, ethology and ecology, especially in birds</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2010/8/6/1281118805708/GrrlScientist-profile-pic-002.jpg\",\"firstName\":\"grrlscientist\",\"lastName\":\"\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/2009/mar/06/fossils-evolution\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2009-03-09T00:00:00Z\",\"webTitle\":\"Science Book Club: Richard Fortey's Life: An Unauthorised Biography\",\"webUrl\":\"https://www.theguardian.com/science/2009/mar/06/fossils-evolution\",\"apiUrl\":\"https://content.guardianapis.com/science/2009/mar/06/fossils-evolution\",\"fields\":{\"headline\":\"Fortey takes us on a companionable journey towards the light\",\"trailText\":\"<p><strong>Tim Radford</strong> gives his verdict on <strong>Life: An Unauthorised Biography</strong> by Richard Fortey. If you've read it, tell us what you think</p>\",\"wordcount\":\"861\"},\"tags\":[{\"id\":\"profile/timradford\",\"type\":\"contributor\",\"webTitle\":\"Tim Radford\",\"webUrl\":\"https://www.theguardian.com/profile/timradford\",\"apiUrl\":\"https://content.guardianapis.com/profile/timradford\",\"references\":[],\"bio\":\"<p>Tim Radford is a freelance journalist and a founding editor of <a href=\\\"http://climatenewsnetwork.net/\\\">Climate News Network</a>. He worked for the Guardian for 32 years, becoming - among other things - letters editor, arts editor, literary editor and science editor. He won the Association of British Science Writers award for science writer of the year four times.</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2007/10/17/tim_radford_140x140.jpg\",\"firstName\":\"radford,\",\"lastName\":\"tim\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/gallery/2013/sep/18/animal-earth-amazing-diversity-life-pictures\",\"type\":\"gallery\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2013-09-18T11:01:00Z\",\"webTitle\":\"The amazing diversity of animal life – in pictures\",\"webUrl\":\"https://www.theguardian.com/science/gallery/2013/sep/18/animal-earth-amazing-diversity-life-pictures\",\"apiUrl\":\"https://content.guardianapis.com/science/gallery/2013/sep/18/animal-earth-amazing-diversity-life-pictures\",\"fields\":{\"headline\":\"The amazing diversity of animal life – in pictures\",\"trailText\":\"<p>The vast majority of animals with which we share our planet go almost unnoticed, eclipsed by bigger, more charismatic creatures</p>\",\"wordcount\":\"0\"},\"tags\":[],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/audio/2013/oct/07/science-weekly-podcast-sex-dark-matter\",\"type\":\"audio\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2013-10-07T04:00:00Z\",\"webTitle\":\"Science Weekly podcast: Sex, dosh and dark matter\",\"webUrl\":\"https://www.theguardian.com/science/audio/2013/oct/07/science-weekly-podcast-sex-dark-matter\",\"apiUrl\":\"https://content.guardianapis.com/science/audio/2013/oct/07/science-weekly-podcast-sex-dark-matter\",\"fields\":{\"headline\":\"Science Weekly podcast: Sex, dosh and dark matter\",\"trailText\":\"<p>Science writer <strong>Marcus Chown</strong> tackles the big stuff in his latest book What a Wonderful World</p>\",\"wordcount\":\"238\"},\"tags\":[],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/blog/2018/jan/05/the-theatre-company-putting-victorian-sci-fi-centre-stage-hg-wells-crystal-egg-live\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2018-01-05T19:04:07Z\",\"webTitle\":\"The theatre company putting Victorian sci-fi centre stage\",\"webUrl\":\"https://www.theguardian.com/science/blog/2018/jan/05/the-theatre-company-putting-victorian-sci-fi-centre-stage-hg-wells-crystal-egg-live\",\"apiUrl\":\"https://content.guardianapis.com/science/blog/2018/jan/05/the-theatre-company-putting-victorian-sci-fi-centre-stage-hg-wells-crystal-egg-live\",\"fields\":{\"headline\":\"The theatre company putting Victorian sci-fi centre stage\",\"trailText\":\"As an adaptation of HG Wells’s The Crystal Egg prepares to open in London, its creators explain how they turned a short story from 1897 into a play for our alien-obsessed times\",\"wordcount\":\"1226\",\"thumbnail\":\"https://media.guim.co.uk/a3fa3b59f584326c5ea0e9ed660480dde5b9c402/0_599_1192_715/500.jpg\"},\"tags\":[{\"id\":\"profile/tash-reith-banks\",\"type\":\"contributor\",\"webTitle\":\"Tash Reith-Banks\",\"webUrl\":\"https://www.theguardian.com/profile/tash-reith-banks\",\"apiUrl\":\"https://content.guardianapis.com/profile/tash-reith-banks\",\"references\":[],\"bio\":\"<p>Tash Reith-Banks is production editor for Guardian Cities. Twitter <a href=\\\"http://twitter.com/TashReithBanks\\\">@TashReithBanks</a></p>\",\"firstName\":\"Tash\",\"lastName\":\"Reith-Banks\",\"twitterHandle\":\"TashReithBanks\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/blog/2015/apr/17/interview-public-service-broadcasting-on-setting-the-space-race-to-music\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2015-04-17T10:00:06Z\",\"webTitle\":\"How JFK and the Cold War space race inspired an album\",\"webUrl\":\"https://www.theguardian.com/science/blog/2015/apr/17/interview-public-service-broadcasting-on-setting-the-space-race-to-music\",\"apiUrl\":\"https://content.guardianapis.com/science/blog/2015/apr/17/interview-public-service-broadcasting-on-setting-the-space-race-to-music\",\"fields\":{\"headline\":\"How JFK and the Cold War space race inspired an album\",\"trailText\":\"Interview: Public Service Broadcasting’s frontman J. Willgoose Esq explores the history behind the band’s Cold War era-inspired album The Race For Space\",\"wordcount\":\"1209\"},\"tags\":[{\"id\":\"profile/marc-burrows\",\"type\":\"contributor\",\"webTitle\":\"Marc Burrows\",\"webUrl\":\"https://www.theguardian.com/profile/marc-burrows\",\"apiUrl\":\"https://content.guardianapis.com/profile/marc-burrows\",\"references\":[],\"bio\":\"<p>Marc Burrows is former Guardian community moderator</p>\",\"bylineImageUrl\":\"https://uploads.guim.co.uk/2016/04/13/Marc-Burrows.jpg\",\"firstName\":\"burrows\",\"lastName\":\"marc\",\"twitterHandle\":\"20thcenturymarc\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/2012/jul/24/sally-ride\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2012-07-24T17:34:16Z\",\"webTitle\":\"Sally Ride obituary\",\"webUrl\":\"https://www.theguardian.com/science/2012/jul/24/sally-ride\",\"apiUrl\":\"https://content.guardianapis.com/science/2012/jul/24/sally-ride\",\"fields\":{\"headline\":\"Sally Ride obituary\",\"trailText\":\"<p>America's first female astronaut whose 1983 space flight captured the world's imagination</p>\",\"wordcount\":\"721\"},\"tags\":[{\"id\":\"profile/reginald-turnill\",\"type\":\"contributor\",\"webTitle\":\"Reginald Turnill\",\"webUrl\":\"https://www.theguardian.com/profile/reginald-turnill\",\"apiUrl\":\"https://content.guardianapis.com/profile/reginald-turnill\",\"references\":[],\"firstName\":\"turnill\",\"lastName\":\"reginald\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/grrlscientist/2015/apr/10/new-books-party-books-that-arrived-recently\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2015-04-10T14:56:35Z\",\"webTitle\":\"New Books Party: books that arrived recently | @GrrlScientist\",\"webUrl\":\"https://www.theguardian.com/science/grrlscientist/2015/apr/10/new-books-party-books-that-arrived-recently\",\"apiUrl\":\"https://content.guardianapis.com/science/grrlscientist/2015/apr/10/new-books-party-books-that-arrived-recently\",\"fields\":{\"headline\":\"New Books Party: books that arrived recently\",\"trailText\":\"<strong>GrrlScientist:</strong> This week’s books include a biochemist’s reasoning that protons are the fundamental reason that life evolved in the way it did; a botanist’s assertion that plants are intelligent beings; and an exploration of one of the basic principles of geology, plate tectonics\",\"wordcount\":\"1118\"},\"tags\":[{\"id\":\"profile/grrlscientist\",\"type\":\"contributor\",\"webTitle\":\"GrrlScientist\",\"webUrl\":\"https://www.theguardian.com/profile/grrlscientist\",\"apiUrl\":\"https://content.guardianapis.com/profile/grrlscientist\",\"references\":[],\"bio\":\"<p>GrrlScientist is an evolutionary biologist and ornithologist who writes about evolution, ethology and ecology, especially in birds</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2010/8/6/1281118805708/GrrlScientist-profile-pic-002.jpg\",\"firstName\":\"grrlscientist\",\"lastName\":\"\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"science/2014/sep/19/ig-nobel-prize-british-researchers-win\",\"type\":\"article\",\"sectionId\":\"science\",\"sectionName\":\"Science\",\"webPublicationDate\":\"2014-09-18T23:30:00Z\",\"webTitle\":\"Ig Nobels: British researchers take coveted science humour prize\",\"webUrl\":\"https://www.theguardian.com/science/2014/sep/19/ig-nobel-prize-british-researchers-win\",\"apiUrl\":\"https://content.guardianapis.com/science/2014/sep/19/ig-nobel-prize-british-researchers-win\",\"fields\":{\"headline\":\"Ig Nobels: British researchers take coveted science humour prize\",\"trailText\":\"UK researchers reveal people who habitually stay up late are, on average, more self-admiring, manipulative and psychopathic\",\"wordcount\":\"792\"},\"tags\":[{\"id\":\"profile/iansample\",\"type\":\"contributor\",\"webTitle\":\"Ian Sample\",\"webUrl\":\"https://www.theguardian.com/profile/iansample\",\"apiUrl\":\"https://content.guardianapis.com/profile/iansample\",\"references\":[],\"bio\":\"<p>Ian Sample is science editor of the Guardian. Before joining the newspaper in 2003, he was a journalist at New Scientist and worked at the Institute of Physics as a journal editor. He has a PhD in biomedical materials from Queen Mary's, University of London. Ian also presents the&nbsp;<a href=\\\"https://www.theguardian.com/science/series/science\\\">Science Weekly podcast</a>.</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2014/4/17/1397749332765/IanSample.jpg\",\"bylineLargeImageUrl\":\"https://uploads.guim.co.uk/2017/10/06/Ian-Sample,-R.png\",\"firstName\":\"sample\",\"lastName\":\"\",\"twitterHandle\":\"iansample\"}],\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";
    private static final int NEWS_LOADER_ID = 2;
    ProgressBar newsProgerssBar;

    public FeaturedNewsFragment(){}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragmentLayout = inflater.inflate(R.layout.fragment_live_news, container, false);
        newsProgerssBar = fragmentLayout.findViewById(R.id.news_progress);

        return fragmentLayout;
    }
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new {@link NewsLoader} instance and pass in the context
        // and the string url to be used in the background
        return new NewsLoader(getActivity(), customJson, false);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        newsProgerssBar.setVisibility(View.GONE);
        updateUi(news);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
    }
    /** Method tu update the UI in the onLoadFinished method */
    private void updateUi(List<News> news){
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView mRecyclerView = fragmentLayout.findViewById(R.id.news_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the custom adapter to the RecyclerView
        RecyclerView.Adapter mAdapter = new NewsAdapter(news);
        mRecyclerView.setAdapter(mAdapter);
    }
}
