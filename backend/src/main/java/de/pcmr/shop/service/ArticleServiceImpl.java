package de.pcmr.shop.service;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ArticleServiceImpl implements ArticleServiceI {

    private final ArticleRepository articleRepository;
    private final ArticleImageServiceI articleImageService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleImageServiceI articleImageService) {
        this.articleRepository = articleRepository;
        this.articleImageService = articleImageService;
    }

    @Override
    public List<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public ArticleEntity getArticle(long articleId) throws NoArticleFoundException {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(articleId);

        if (articleEntity.isPresent()) {
            return articleEntity.get();
        } else {
            throw new NoArticleFoundException();
        }
    }

    @Override
    public void createNewArticle(@Valid ArticleEntity articleEntity) {
        articleRepository.save(articleEntity);
    }

    @Override
    public void updateArticle(@Valid ArticleEntity articleEntity) throws NoArticleFoundException {
        if (articleRepository.existsById(articleEntity.getId())) {
            articleRepository.save(articleEntity);
        } else {
            throw new NoArticleFoundException();
        }
    }

    @Override
    public void deleteArticle(long articleId) throws NoArticleFoundException, IOException {
        if (articleRepository.existsById(articleId)) {
            articleImageService.deleteArticleImages(articleId);
            articleRepository.deleteById(articleId);
        } else {
            throw new NoArticleFoundException();
        }
    }
}